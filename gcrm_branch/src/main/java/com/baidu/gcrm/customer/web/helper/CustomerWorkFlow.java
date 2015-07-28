package com.baidu.gcrm.customer.web.helper;

import java.util.ArrayList;
import java.util.List;

public enum CustomerWorkFlow {
    startUser {
        @Override
        public List<String> getNextApproveRole(boolean isAddPlus) {
            List<String> resultRole = new ArrayList<String>();
            resultRole.add(agent_leader.toString());
            resultRole.add(sales_leader.toString());
            return resultRole;
        }
        @Override
        public boolean isCanAddPlus() {
            return false;
        }
    },
    agent_leader {
        @Override
        public List<String> getNextApproveRole(boolean isAddPlus) {
            List<String> resultRole = new ArrayList<String>();
            resultRole.add(Level_approval.toString());
            return resultRole;
        }

        @Override
        public boolean isCanAddPlus() {
            return false;
        }
    },
    sales_leader {
        @Override
        public List<String> getNextApproveRole(boolean isAddPlus) {
            List<String> resultRole = new ArrayList<String>();
            if(isAddPlus)
                resultRole.add(Superior_superior.toString());
            return resultRole;
        }

        @Override
        public boolean isCanAddPlus() {
            return true;
        }
    }, // 发起者上级
    Level_approval {
        @Override
        public   List<String> getNextApproveRole(boolean isAddPlus) {
            List<String> resultRole = new ArrayList<String>();

            if (isAddPlus) {
              //  resultRole.add(Global_ceo.toString());
            } else {
                resultRole.add(Level_approval.toString());
            }
            return resultRole;
        }

        @Override
        public     boolean isCanAddPlus() {
            return true;
        }
    }, // 逐级审批
    Global_ceo{
        @Override
        public   List<String> getNextApproveRole(boolean isAddPlus) {
            return null;
        }

        @Override
        public      boolean isCanAddPlus() {
            return false;
        }
    }, // 国际化执行总监
    Superior_superior {
        @Override
        public     List<String> getNextApproveRole(boolean isAddPlus) {
            return null;
        }

        @Override
        public    boolean isCanAddPlus() {
            return false;
        }
    };// 上级的上级

public    abstract List<String> getNextApproveRole(boolean isAddPlus);

public  abstract boolean isCanAddPlus();
}
