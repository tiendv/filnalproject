package uit.qabpss.util.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import uit.qabpss.dbconfig.ColumnInfo;
import org.hibernate.Query;
import uit.qabpss.dbconfig.DBInfo;
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;
import uit.qabpss.preprocess.TripleWord;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	static {
		sessionFactory = new  Configuration().configure().buildSessionFactory();
                DBInfoUtil.initDb();

	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
        public static void closeSessionFactory() {
		sessionFactory.close();
	}

        public static TripleWord getTripleFromValue(String value) {
            if (value.isEmpty()) {
                return null;
            }
            String temp = value;
            TripleWord t = null;

            try {
                int num = Integer.parseInt(temp);
                System.out.println("is number");
                t = checkValueFromDB(value, Type.INTEGER);
                return t;
            } catch (Exception e) {
                //do nothing
            }

            if ((value + " ").matches("[0-9].*")) {
                t = checkValueFromDB(value, Type.CODE);
                return t;
            }

            t = checkValueFromDB(value, Type.STRING);
            if (t != null) {
                return t;
            }
            return null;
        }

        private static TripleWord checkValueFromDB(String value,Type type){
            TripleWord t = null;
            String inputValue = "";
            List<TableInfo> tables = DBInfoUtil.getDBInfo().getTables();
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                for (int i = 0; i < tables.size(); i++) {
                    TableInfo tableInfo = tables.get(i);
                    List<ColumnInfo> columns = tableInfo.getColumns();
                    for (int j = 0; j < columns.size(); j++) {
                        ColumnInfo columnInfo = columns.get(j);
                        if("titleSignature".equals(columnInfo.getName())){
                            inputValue = inputValue.replace(" ","");
                            inputValue = inputValue.replace(".","");
                            inputValue = inputValue.trim();
                        } else {
                            if (type.equals(Type.STRING) || type.equals(Type.CODE)) {
                                inputValue = "'" + value + "'";
                            } else {
                                inputValue = value;
                            }
                        }
                        if (type.equals(columnInfo.getType())) {
                            List result = new ArrayList();                            
                            System.out.println("from " + tableInfo.getAliasName() + " where " + columnInfo.getName() + " = " + inputValue);
                            Query q = session.createQuery("from " + tableInfo.getAliasName() + " where " + columnInfo.getName() + " = " + inputValue);
                            q.setMaxResults(1);
                            result = q.list();
                            if (result.size() > 0) {
                                t = new TripleWord("", "", "");
                                t.setFirstObject(tableInfo.getAliasName());
                                t.setRelationWord("has " + columnInfo.getAliasName().toLowerCase());
                                t.setSecondObject(value);
                                return t;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            return null;
        }

        public static void main(String[] args){
//            TripleWord t = HibernateUtil.getTripleFromValue("1999");
//             TripleWord t = HibernateUtil.getTripleFromValue("ACM");
//             TripleWord t = HibernateUtil.getTripleFromValue("Elsevier");
//             TripleWord t = HibernateUtil.getTripleFromValue("On Resolving Schematic Heterogeneity in Multidatabase Systems");
             TripleWord t = HibernateUtil.getTripleFromValue("Spatial Data Structures.");
            //test with DOI values
//            TripleWord t = HibernateUtil.getTripleFromValue("10.1007/BFb0015242");
//            TripleWord t = HibernateUtil.getTripleFromValue("10.2200/S00193ED1V01Y200905CAC006");
//              TripleWord t = HibernateUtil.getTripleFromValue("10.1109/IM.2003.1240282");
            //Test with ISBN values
//            TripleWord t = HibernateUtil.getTripleFromValue("0-201-59098-0");
//            TripleWord t = HibernateUtil.getTripleFromValue("0-201-39829-X");
//            TripleWord t = HibernateUtil.getTripleFromValue("978-3-540-73108-5");
            System.out.println("<" + t.getFirstObject() + "," + t.getRelationWord() + "," + t.getSecondObject() + ">");
        }
}
