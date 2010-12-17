/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.qabpss.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.ImageButtonBean;
import org.apache.struts.util.MessageResources;
import uit.qabpss.dbconfig.DBInfoUtil;
import uit.qabpss.dbconfig.Param;
import uit.qabpss.dbconfig.TableInfo;
import uit.qabpss.dbconfig.Type;

/**
 *
 * @author ThuanHung
 */
public class AdvanceSearchForm extends org.apache.struts.action.ActionForm {

    private List<Param> params;
    private ImageButtonBean submit;
    private TableInfo tableInfo;
    private boolean isAndOperator;

    public boolean isIsAndOperator() {
        return isAndOperator;
    }

    public void setIsAndOperator(boolean isAndOperator) {
        this.isAndOperator = isAndOperator;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public ImageButtonBean getSubmit() {
        return submit;
    }

    public void setSubmit(ImageButtonBean submit) {
        this.submit = submit;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public void setParam(int index, Param param) {
        if (index > params.size()) {
            params.set(index, param);
        }
    }

    public Param getParam(int index) {
        return params.get(index);
    }

    /**
     *
     */
    public AdvanceSearchForm() {
        submit = new ImageButtonBean();

        params = new ArrayList<Param>();
        isAndOperator = true;
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        MessageResources msgRes = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);
        Locale locale = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
        boolean empty = true;
        for (int i = 0; i < params.size(); i++) {
            Param param = params.get(i);
            if (!param.getValue().isEmpty()) {
                empty = false;
            }
        }
        if (empty == false) {
            for (Param param : params) {
                if (!param.getValue().isEmpty()) {
                    if (param.getColumn().getType().equals(Type.INTEGER) || param.getColumn().getType().equals(Type.LONG)) {
                        System.out.println(param.getValue());
                        try {
                            Integer.parseInt(param.getValue());
                        } catch (Exception ex) {
                            String dbName = DBInfoUtil.getDBInfo().getName();
                            if (!dbName.equals("")) {
                                dbName += ".";
                            }

                            String fieldMsg = param.getColumn().getAliasName();
                            if (locale != null) {
                                fieldMsg = msgRes.getMessage(locale, "text." + dbName + param.getColumn().getAliasName().toLowerCase());
                            } else {
                                fieldMsg = msgRes.getMessage("text." + dbName + param.getColumn().getAliasName().toLowerCase());
                            }
                            errors.add(param.getColumn().getAliasName(), new ActionMessage("errors.isnumber", new String[]{fieldMsg}));
                        }
                    }
                    if(param.getColumn().getType().equals(Type.STRING)){
                        if(param.getValue().length()<3){
                        errors.add( param.getColumn().getAliasName().toLowerCase(), new ActionMessage("errors.minlength",new String[]{ param.getColumn().getAliasName().toLowerCase(),"3"}));
                        }     
                    }
                }
            }
        } else {
            errors.add(null, new ActionMessage("errors.input"));
        }
        return errors;
    }
}