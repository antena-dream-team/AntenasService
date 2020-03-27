package br.gov.sp.fatec.project.fixture;

import br.gov.sp.fatec.project.domain.Project;

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
//                .status()
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
//                .status()
//                .meeting()
//                .shortDescription()
//                .students()
//                .studentResponsible()
//                .teacher()
//                .technologyDescription()
//                .title()
                .build();
    }
}
