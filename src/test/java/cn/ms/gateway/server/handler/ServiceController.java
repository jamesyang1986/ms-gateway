package cn.ms.gateway.server.handler;

import cn.ms.gateway.server.common.RequestMethod;
import cn.ms.gateway.server.common.annotations.Controller;
import cn.ms.gateway.server.common.annotations.Header;
import cn.ms.gateway.server.common.annotations.PathVariable;
import cn.ms.gateway.server.common.annotations.RequestBody;
import cn.ms.gateway.server.common.annotations.RequestMapping;
import cn.ms.gateway.server.common.annotations.RequestParam;
import cn.ms.gateway.server.model.ProjectModel;
import cn.ms.gateway.server.model.ServiceResponse;
import cn.ms.gateway.server.model.Type;

@Controller
@RequestMapping("/projects")
public class ServiceController {

    // [POST] http://host:port/projects/1
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ServiceResponse createProject(@RequestParam(value = "not-exist", required = false) int nouse, @RequestBody ProjectModel project) {
        System.out.println("createProject() projectName " + project.getProjectName() + " nouse " + nouse);
        return new ServiceResponse();
    }

    // [GET] http://host:port/projects/1
    @RequestMapping("/type/{type}")
    public ServiceResponse getProjectByType(@PathVariable("type") Type type) {
        System.out.println("getProjectByType() type " + type);
        return new ServiceResponse();
    }

    // [GET] http://host:port/projects/1
    @RequestMapping("/{projectId}")
    public ServiceResponse getProjectById(@PathVariable("projectId") Integer projectId) {
        System.out.println("getProjectById() projectId " + projectId);
        return new ServiceResponse();
    }

    // [GET] http://host:port/projects
    @RequestMapping("/")
    public ServiceResponse getAllProjects(@Header(value = "Content-Type", required = false) String contentType) {
        return new ServiceResponse();
    }

    // [GET] http://host:port/projects/name/my_project1?owner=nesty
    @RequestMapping("/name/{projectName}")
    public ServiceResponse getProjectByName(@PathVariable("projectName") String projectName,
                                            @RequestParam(value = "owner", required = false) String owner) {
        System.out.println("getProjectByNam() projectName " + projectName + ", owner " + owner);
        return new ServiceResponse();
    }

    // [GET] http://host:port/projects/name/my_project1/property/id?owner=nesty
    @RequestMapping("/name/{projectName}/property/{id}")
    public ServiceResponse getProjectByName(@PathVariable("projectName") String projectName, @PathVariable("id") String property,
                                            @RequestParam(value = "owner", required = false) String owner) {
        System.out.println("getProjectByNam() projectName " + projectName + ", id" + property);
        return new ServiceResponse();
    }

    // [PUT] http://host:port/projects/1
    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public ServiceResponse updateProjectNameById(@PathVariable("projectId") Integer projectId, @RequestParam("projectName") String projectName,
                                                 @RequestBody ProjectModel project) {
        System.out.println("updateProjectNameById projectId " + projectId + ". projectName " + project.getProjectName());
        return new ServiceResponse();
    }
}
