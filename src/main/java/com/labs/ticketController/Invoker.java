package com.labs.ticketController;


import com.labs.common.AbstractInvoker;
import com.labs.ticketController.commands.AddCommand;
import com.labs.ticketController.commands.AddIfMaxCommand;
import com.labs.ticketController.commands.AddIfMinCommand;
import com.labs.ticketController.commands.AddSomeCommand;
import com.labs.ticketController.commands.AverageOfPriceCommand;
import com.labs.ticketController.commands.ClearCommand;
import com.labs.ticketController.commands.CountGreaterThanRefundableCommand;
import com.labs.ticketController.commands.RemoveByIdCommand;
import com.labs.ticketController.commands.ShowCommand;
import com.labs.ticketController.commands.UpdateCommand;
import com.labs.ticketController.commands.FilterGreaterThanRefundableCommand;
import com.labs.ticketController.commands.InfoCommand;


public class Invoker extends AbstractInvoker {
    public Invoker() {
        CollectionManager cm = new CollectionManager();
        commands.put("add", new AddCommand(cm));
        commands.put("addSome", new AddSomeCommand(cm));
        commands.put("update", new UpdateCommand(cm));
        commands.put("show", new ShowCommand(cm));
        commands.put("remove_by_id", new RemoveByIdCommand(cm));
        commands.put("clear", new ClearCommand(cm));
        commands.put("average_of_price", new AverageOfPriceCommand(cm));
        commands.put("count_greater_than_refundable", new CountGreaterThanRefundableCommand(cm));
        commands.put("filter_greater_than_refundable", new FilterGreaterThanRefundableCommand(cm));
        commands.put("add_if_max", new AddIfMaxCommand(cm));
        commands.put("add_if_min", new AddIfMinCommand(cm));
        commands.put("info", new InfoCommand(cm));
    }
}
