package br.gov.sp.fatec.project.fixture;

import br.gov.sp.fatec.project.domain.Date;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.project.domain.Status;

import java.util.LinkedList;
import java.util.List;

import static br.gov.sp.fatec.entrepreneur.fixture.EntrepreneurFixture.newEntrepreneur;

public class ProjectFixture {

    private static final String COMPLETE_DESCRIPTION = "projeto que faz uma banana de cimento comestível para combater carrapatos em cinemas";

    public static Project newProject() {
        return  Project.builder()
                .completeDescription(COMPLETE_DESCRIPTION)
                .entrepreneur(newEntrepreneur())
//                .externalLink1()
//                .externalLink2()
                .id(1L)
//                .progress()
                .status(newStatus())
//                .meeting()
//                .shortDescription()
//                .students()
//                .studentResponsible()
//                .teacher()
//                .technologyDescription()
//                .title()
                .build();
    }

    public static Project newProject(Long id) {
        return  Project.builder()
                .completeDescription(COMPLETE_DESCRIPTION)
                .entrepreneur(newEntrepreneur())
                .id(id)
//                .externalLink1()
//                .externalLink2()
//                .progress()
                .status(newStatus())
//                .meeting()
//                .shortDescription()
//                .students()
//                .studentResponsible()
//                .teacher()
//                .technologyDescription()
//                .title()
                .build();
    }

    public static Status newStatus() {
        return Status.builder()
                .denied(false)
                .id(1L)
                .reason("Not acceptable")
                .build();
    }

    public static Date newDate(Long id) {
        // todo - em vez de ter uma tabela de data separada, ter direto a tabela da relação com a data e o id projeto
        return Date.builder()
                .id(id)
                .dateTime(new java.util.Date())
                .build();
    }

    public static List<Date> getPossibleDate() {
        List<Date> dateList = new LinkedList<>();

        for (long i = 0; i < 3; i++) {
            dateList.add(newDate(i));
        }

        return dateList;
    }
}
