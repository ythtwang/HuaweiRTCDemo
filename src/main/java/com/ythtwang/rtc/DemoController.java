package com.ythtwang.rtc;

import com.ythtwang.rtc.model.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private Sms smsRequest;

    /**
     * @param receiver,
     * @param statuscallback,
     * @param templateParas,
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/smsRequest", method = RequestMethod.GET)
    @ResponseBody
    public String sms(@RequestParam(value = "receiver", required = true) String receiver,
                      @RequestParam(value = "statuscallback", required = true, defaultValue = "") String statuscallback,
                      @RequestParam(value = "templateParas", required = true, defaultValue = "[\"369751\"]") String templateParas
    ) throws Exception {
        System.out.println("receiver: " + receiver);
        System.out.println("statuscallback: " + statuscallback);
        System.out.println("templateParas: " + templateParas);
        smsRequest.send(receiver, statuscallback, templateParas);
        return "send smsRequest to (" + receiver + ") successfully. \n ";
    }

}