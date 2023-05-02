package pers.yiran.booklending.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yiran
 */
public class IdRole {
    private IdRole(){}
    private static Map<Integer, Integer> idRoleMap = new HashMap<>();
    private static class IdRoleInit {
        private static final IdRole ID_ROLE = new IdRole();
    }
    public static IdRole getInstance(){
        return IdRoleInit.ID_ROLE;
    }
    public Map<Integer, Integer> getIdRoleMap(){
        return idRoleMap;
    }
    public static void setIdRoleMap(Map<Integer, Integer> idRoleMap){
        IdRole.idRoleMap = idRoleMap;
    }
}