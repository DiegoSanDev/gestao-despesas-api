package br.com.devspraticar.gestaodespesas.service;

import br.com.devspraticar.gestaodespesas.exception.InternalServerErrorException;
import br.com.devspraticar.gestaodespesas.exception.InvalidInstallmentStartDateException;
import br.com.devspraticar.gestaodespesas.model.Expense;
import br.com.devspraticar.gestaodespesas.model.ExpenseParcel;
import br.com.devspraticar.gestaodespesas.model.ParcelControl;
import br.com.devspraticar.gestaodespesas.repository.ExpenseParcelRepository;
import br.com.devspraticar.gestaodespesas.repository.ExpenseRepository;
import br.com.devspraticar.gestaodespesas.repository.ParcelControlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseParcelRepository expenseParcelRepository;
    private final ParcelControlRepository parcelControlRepository;

    @Transactional
    public Expense create(Expense expense) {
        log.info("Criar expense: {}", expense);
        if(!expense.isInstallmentStartDateValid()) {
            throw new InvalidInstallmentStartDateException();
        }
        return saveExpense(expense);
    }

    private Expense saveExpense(Expense expense) {
        try {
            expense = expenseRepository.create(expense);
            ExpenseParcel expenseParcel = saveParcel(expense);
            saveParcelControl(expenseParcel, expense.getAmount());
            expense.setParcel(expenseParcel);
        } catch (Exception e) {
            log.error("Erro genérico ao tentar criar a expense: {}", expense, e);
            throw new InternalServerErrorException();
        }
        return expense;
    }

    private ExpenseParcel saveParcel(Expense expense) {
        ExpenseParcel expenseParcel = null;
        if(expense.existsParcel()) {
            var parcel = expense.getParcel();
            parcel.setIdExpense(expense.getId());
            expenseParcel = expenseParcelRepository.save(parcel);
        }
        return expenseParcel;
    }

    private void saveParcelControl(ExpenseParcel expenseParcel, BigDecimal amount) {
        if(nonNull(expenseParcel) && nonNull(amount)) {
            List<ParcelControl> parcels  = new ArrayList<>();
            BigDecimal amountParcel = amount.divide(new BigDecimal(expenseParcel.getQuantity()), 2, RoundingMode.HALF_UP);
            IntStream.range(0, expenseParcel.getQuantity())
                .forEach(indexQuantity -> {
                    var parcelControl = getParcelControl(expenseParcel, amountParcel, indexQuantity);
                    parcelControlRepository.save(parcelControl);
                    parcels.add(parcelControl);
                });
            expenseParcel.setParcels(parcels);
        }
    }

    private ParcelControl getParcelControl(ExpenseParcel expenseParcel, BigDecimal amountExpense, int indexQuantity) {
        LocalDate monthPayment = expenseParcel.getStartDate().plusMonths(indexQuantity -1L);
        return ParcelControl.builder()
            .amount(amountExpense)
            .protocol(UUID.randomUUID())
            .parcelId(expenseParcel.getId())
            .monthPayment(monthPayment.toString().substring(0, 7))
            .build();
    }
}