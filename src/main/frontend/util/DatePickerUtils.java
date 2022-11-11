package main.frontend.util;

import main.utils.DateUtils;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

public class DatePickerUtils {


    /**
     * function to create date picker library
     */
    public static JDatePickerImpl createNewDatePicker() {
        DateModel<Date> model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }

    /**
     * @return date from date picker
     */
    public static LocalDate getDateFromDatePicker(JDatePicker datePicker) {
        DateModel model = datePicker.getModel();

        if (model.getValue() == null) {
            return null;
        }

        String day = String.format("%02d", model.getDay());
        String month = String.format("%02d", model.getMonth() + 1);
        String year = String.format("%04d", model.getYear());
        return DateUtils.stringToDate(year + "-" + month + "-" + day);
    }

    /** refresh date picker*/
    public static void refreshDatePicker(JDatePicker datePicker) {
        datePicker.getModel().setValue(null);
    }

    /** set date for the date picker library*/
    public static void setDatePickerSelectedDate(JDatePickerImpl paymentDatePicker, LocalDate date) {
        DateModel<?> model = paymentDatePicker.getModel();
        model.setDate(date.getYear(), date.getMonth().getValue() - 1, date.getDayOfMonth());
        model.setSelected(true);
    }
}
