/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uit.qass.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uit.qass.core.search.searchPublication;
import uit.qass.model.Publication;

/**
 *
 * @author aa
 */
public class showPubDetailAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
     private static final String WARNING = "There are no references now !";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String id = request.getParameter("id");
        Publication publication = searchPublication.searchPubByID(id);
        request.setAttribute("publication", publication);
        if(publication.getRefPubs().size()==0){
            request.setAttribute("warning", WARNING);
        }
        return mapping.findForward(SUCCESS);
    }
}
