package com.example.trainhome.schedulers;

import com.example.trainhome.dto.TransactionsDTO;
import com.example.trainhome.entities.Credit;
import com.example.trainhome.exceptions.InvalidDataException;
import com.example.trainhome.repositories.CreditRepository;
import com.example.trainhome.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class PayCreditService {
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private TransactionsService transactionsService;

    @Scheduled(cron = "0 0 0 * * *")
    public void reportCurrentTime() throws InvalidDataException {
        List<Credit> all = creditRepository.findAll();
        for (Credit credit : all) {
            if (credit.getNextPay().toLocalDate().isBefore(LocalDate.now()) && credit.getPayedMonths() < credit.getCommonCountMonths()) {
                transactionsService.createTransaction(new TransactionsDTO(
                        Date.valueOf(LocalDate.now()),
                        credit.getCoachId().getId(),
                        credit.getMoney()
                ));
                credit.setNextPay(Date.valueOf(LocalDate.now().plusMonths(1)));
                credit.setPayedMonths(credit.getPayedMonths() + 1);
                creditRepository.save(credit);
            }
        }
    }
}
