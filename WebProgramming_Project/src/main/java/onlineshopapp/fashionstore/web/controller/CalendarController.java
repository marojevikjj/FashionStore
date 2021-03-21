package onlineshopapp.fashionstore.web.controller;

import onlineshopapp.fashionstore.service.EventService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
class CalendarController {

    private final EventService eventService;

    public CalendarController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value="/calendar", method=RequestMethod.GET)
    public ModelAndView calendar() {
        return new ModelAndView("calendar");
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/admin-calendar", method=RequestMethod.GET)
    public ModelAndView adminCalendar() {
        return new ModelAndView("admin-calendar");
    }

    @RequestMapping(value="/eventlist", method=RequestMethod.GET)
    public String events(HttpServletRequest request, Model model) {
        model.addAttribute("events", this.eventService.findAll());
        return "events";
    }
}