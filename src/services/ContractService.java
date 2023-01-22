package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService  {
    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }
    public void processContract(Contract contract, int mounths) {

        double basicQuota = contract.getTotalValue() / mounths;

        for (int i = 1; i < mounths; i++) {
            LocalDate dueDate=  contract.getDate().plusMonths(i);

            double interest = onlinePaymentService.interest(basicQuota, i);
            double fee = onlinePaymentService.paymentFee(basicQuota + interest);
            double quota = basicQuota + interest + fee;

            contract.getInstallments().add(new Installment(dueDate, quota));

        }

    }
}
