package br.gov.sp.fatec.project.controller.converter;

import br.gov.sp.fatec.entrepreneur.controller.converter.EntrepreneurConverter;
import br.gov.sp.fatec.entrepreneur.controller.dto.EntrepreneurDTO;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.project.controller.dto.DateDTO;
import br.gov.sp.fatec.project.controller.dto.MeetingDTO;
import br.gov.sp.fatec.project.controller.dto.ProjectDTO;
import br.gov.sp.fatec.project.domain.Date;
import br.gov.sp.fatec.project.domain.Meeting;
import br.gov.sp.fatec.project.domain.Project;
import br.gov.sp.fatec.teacher.controller.converter.TeacherConverter;
import br.gov.sp.fatec.teacher.controller.dto.TeacherDTO;
import br.gov.sp.fatec.teacher.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectConverter {

    @Autowired
    private TeacherConverter teacherConverter;

    @Autowired
    private EntrepreneurConverter entrepreneurConverter;


    public Project toModel(ProjectDTO dto) {
        Project model = new Project();

        model.setCompleteDescription(dto.getCompleteDescription());
        model.setPhase(dto.getPhase());
        model.setKey(dto.getKey());
        model.setId(dto.getId());
        model.setExternalLink2(dto.getExternalLink2());
        model.setExternalLink1(dto.getExternalLink1());
        model.setEntrepreneur(entrepreneurConverter.toModel(dto.getEntrepreneur()));
        model.setMeeting(meetingToModel(dto.getMeeting()));
        model.setTeacher(teacherConverter.toModel(dto.getTeacher()));
        model.setTecnologyDescription(dto.getTecnologyDescription());
        model.setCompleteDescription(dto.getCompleteDescription());
        model.setShortDescription(dto.getShortDescription());

        return model;
    }


    public Meeting meetingToModel(MeetingDTO dto) {
        Meeting model = new Meeting();

        model.setAddress(dto.getAddress());
        model.setChosenDate(dto.getChosenDate());
        model.setId(dto.getId());
        model.setPossible_date(dateToModel(dto.getPossible_date()));

        return model;
    }

    public List<Date> dateToModel(List<DateDTO> dtos) {
        List<Date> list = new LinkedList<>();

        for (DateDTO dateDTO : dtos) {
            Date model = new Date();

            model.setDateTime(dateDTO.getDateTime());
            model.setId(dateDTO.getId());

            list.add(model);
        }

        return list;
    }

    public ProjectDTO toDTO(Project model) {
        ProjectDTO dto = new ProjectDTO();

        dto.setCompleteDescription(model.getCompleteDescription());
        dto.setPhase(model.getPhase());
        dto.setKey(model.getKey());
        dto.setId(model.getId());
        dto.setExternalLink2(model.getExternalLink2());
        dto.setExternalLink1(model.getExternalLink1());
        dto.setEntrepreneur(entrepreneurConverter.toDTO(model.getEntrepreneur()));
        dto.setMeeting(meetingToDTO(model.getMeeting()));
        dto.setTeacher(teacherConverter.toDTO(model.getTeacher()));
        dto.setTecnologyDescription(model.getTecnologyDescription());
        dto.setCompleteDescription(model.getCompleteDescription());
        dto.setShortDescription(model.getShortDescription());

        return dto;
    }


    public MeetingDTO meetingToDTO(Meeting model) {
        MeetingDTO dto = new MeetingDTO();

        dto.setAddress(model.getAddress());
        dto.setChosenDate(model.getChosenDate());
        dto.setId(model.getId());
        dto.setPossible_date(dateToDTO(model.getPossible_date()));

        return dto;
    }

    public List<DateDTO> dateToDTO(List<Date> models) {
        List<DateDTO> list = new LinkedList<>();

        for (Date date : models) {
            DateDTO dto = new DateDTO();

            dto.setDateTime(date.getDateTime());
            dto.setId(date.getId());

            list.add(dto);
        }

        return list;
    }
}