package br.gov.sp.fatec.project.controller.dto;

import br.gov.sp.fatec.entrepreneur.controller.dto.EntrepreneurDTO;
import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String title;
    private String shortDescription;
    private String completeDescription;
    private String tecnologyDescription;
    private String externalLink1;
    private String externalLink2;
    private String phase;
    private MeetingDTO meeting;
    private TeacherDTO teacher;
    private EntrepreneurDTO entrepreneur;
    private String key;
//    private StatusDTO status; TODO
}
