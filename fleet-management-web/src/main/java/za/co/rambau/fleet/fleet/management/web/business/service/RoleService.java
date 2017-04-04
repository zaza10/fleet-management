/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.rambau.fleet.fleet.management.web.business.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import za.co.rambau.fleet.fleet.management.data.controllers.DataFacade;
import za.co.rambau.fleet.fleet.management.data.entities.Role;

/**
 *
 * @author fhatu
 */
@Stateless
@Path("Role")
public class RoleService {

   // @Inject
    private DataFacade dataFacade;

    public RoleService() {

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Role entity) {        
        dataFacade.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Role entity) {
        dataFacade.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        dataFacade.remove(dataFacade.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Role find(@PathParam("id") Long id) {
        return (Role)dataFacade.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Role> findAll() {
        return dataFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Role> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return dataFacade.findRange(from,to);
    }

   }
