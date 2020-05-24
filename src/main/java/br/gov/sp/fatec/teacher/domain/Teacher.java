package br.gov.sp.fatec.teacher.domain;

import br.gov.sp.fatec.User.Domain.User;
import br.gov.sp.fatec.project.domain.Deliver;
import br.gov.sp.fatec.project.view.ProjectView;
import br.gov.sp.fatec.teacher.view.TeacherView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "id")
public class Teacher extends User {

}
