package cn.ms.gateway.server.model;

import cn.ms.gateway.server.common.annotations.Controller;

@Controller
public class ProjectModel {

    private Integer projectId;
    private String projectName;
    private String projectOwener;

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectOwener() {
        return projectOwener;
    }
}

