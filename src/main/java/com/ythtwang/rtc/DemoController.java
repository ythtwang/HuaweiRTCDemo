package com.ythtwang.rtc;

import com.ythtwang.rtc.model.pns.*;
import com.ythtwang.rtc.model.sms.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private Sms smsDemo;

    @Autowired
    private AX axDemo;

    @Autowired
    private AXB axbDemo;

    @Autowired
    private AXE axeDemo;

    @Autowired
    private AXYB axybDemo;

    @Autowired
    private X xDemo;

    /**
     * @param receiver,
     * @param statuscallback,
     * @param templateParas,
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sms", method = RequestMethod.GET)
    @ResponseBody
    public String sms(@RequestParam(value = "receiver", required = true) String receiver,
                      @RequestParam(value = "statuscallback", required = true, defaultValue = "") String statuscallback,
                      @RequestParam(value = "templateParas", required = true, defaultValue = "[\"36975101\"]") String templateParas
    ) throws Exception {
        System.out.println("receiver: " + receiver);
        System.out.println("statuscallback: " + statuscallback);
        System.out.println("templateParas: " + templateParas);
        if (!smsDemo.send(receiver, statuscallback, templateParas)) {
            return "send sms to (" + receiver + ") fail. \nHUAWEI RTC Response StateCode: " + smsDemo.getStateCode() +
                    "\nHUAWEI RTC Entity: " + smsDemo.getSmsResult() + "\n";
        }
        return "send sms to (" + receiver + ") successfully. \n ";
    }

    @RequestMapping(value = "/ax", method = RequestMethod.GET)
    @ResponseBody
    public String ax(@RequestParam(value = "A", required = true) String aNumber,
                     @RequestParam(value = "X", required = true) String xNumber
    ) throws Exception {
        System.out.println("A: " + aNumber);
        System.out.println("X: " + xNumber);
        axDemo.run(aNumber, xNumber);

        return "run private number AX demo (A:" + aNumber + ", X:" + xNumber + " ) successfully. \n ";
    }

    @RequestMapping(value = "/axb", method = RequestMethod.GET)
    @ResponseBody
    public String axb(@RequestParam(value = "A", required = true) String aNumber,
                      @RequestParam(value = "X", required = true) String xNumber,
                      @RequestParam(value = "B", required = true) String bNumber
    ) throws Exception {
        System.out.println("A: " + aNumber);
        System.out.println("X: " + xNumber);
        System.out.println("B: " + bNumber);
        axbDemo.run(aNumber, xNumber, bNumber);

        return "run private number AXB demo (A:" + aNumber + ", X:" + xNumber + ", B:" + bNumber + " ) successfully. \n ";
    }

    @RequestMapping(value = "/axe", method = RequestMethod.GET)
    @ResponseBody
    public String axe(@RequestParam(value = "A", required = true) String aNumber,
                      @RequestParam(value = "X", required = true) String xNumber
    ) throws Exception {
        System.out.println("A: " + aNumber);
        System.out.println("X: " + xNumber);
        axeDemo.run(aNumber, xNumber);

        return "run private number AXE demo (A:" + aNumber + ", X:" + xNumber + " ) successfully. \n ";
    }

    @RequestMapping(value = "/axyb", method = RequestMethod.GET)
    @ResponseBody
    public String axyb(@RequestParam(value = "A", required = true) String aNumber,
                       @RequestParam(value = "X", required = true) String xNumber
    ) throws Exception {
        System.out.println("A: " + aNumber);
        System.out.println("X: " + xNumber);
        axybDemo.run(aNumber, xNumber);

        return "run AXYB demo (A:" + aNumber + ", X:" + xNumber + " ) successfully. \n ";
    }

    @RequestMapping(value = "/x", method = RequestMethod.GET)
    @ResponseBody
    public String x(@RequestParam(value = "X", required = true) String xNumber
    ) throws Exception {
        System.out.println("X: " + xNumber);
        xDemo.run(xNumber);

        return "run private number X demo X:" + xNumber + " ) successfully. \n ";
    }
}