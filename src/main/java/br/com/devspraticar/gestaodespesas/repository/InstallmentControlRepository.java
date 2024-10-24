package br.com.devspraticar.gestaodespesas.repository;

import br.com.devspraticar.gestaodespesas.model.InstallmentControl;

import java.util.List;

public interface InstallmentControlRepository {

    void save(InstallmentControl installmentControl);

    List<InstallmentControl> findAllByInstallmentId(Long installmentId);

}
