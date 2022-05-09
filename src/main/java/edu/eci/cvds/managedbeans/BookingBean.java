package edu.eci.cvds.managedbeans;


import edu.eci.cvds.entities.Booking;
import edu.eci.cvds.entities.Resource;
import edu.eci.cvds.services.ECIStuffServices;
import edu.eci.cvds.services.ServicesException;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ManagedBean(name = "bookingBean")
@RequestScoped
public class BookingBean extends BasePageBean{
    private Date fechaInicio;
    private Date fechaFin;
    @Inject
    private ECIStuffServices eciStuffServices;

    @Setter @Getter private Booking booking;

    public void registerBooking(Date fechaInicio, Date fechaFin,int userId,int resourceId) throws ServicesException, ParseException {
        try {
            long timeInMilliSeconds = fechaInicio.getTime();
            java.sql.Date date1 = new java.sql.Date(timeInMilliSeconds);
            long timeInMilliSeconds2 = fechaFin.getTime();
            java.sql.Date date2 = new java.sql.Date(timeInMilliSeconds2);
            eciStuffServices.registerBooking(date1,date2,userId,resourceId);
        }catch (Exception e){
            throw e;
        }
    }

    public Booking consultBooking() throws ServicesException {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("------------"+id+"---------------------");
            booking =  eciStuffServices.consultBooking(id);
            return booking;
        } catch (ServicesException ex) {
            throw new ServicesException(ex.getMessage());
        }
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
