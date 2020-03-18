package br.gov.sp.fatec.teacher.controller;

import br.gov.sp.fatec.teacher.domain.Teacher;
import br.gov.sp.fatec.teacher.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.gov.sp.fatec.teacher.fixture.TeacherFixture.newTeacher;
import static br.gov.sp.fatec.utils.commons.JSONParser.toJSON;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;





@RunWith(MockitoJUnitRunner.class)
public class teacherControllerTest {

    private static final String URL = "/employee";

    @InjectMocks
    private  TeacherController controller;

    @Mock
    private TeacherService service;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

//    @Test
//    public void create_shouldSucceed() throws Exception {
//        Teacher teacher = newTeacher();
//        when(service.save(teacher)).thenReturn(teacher);
//
//        mockMvc.perform(post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJSON(teacher)))
//                .andExpect(status().isCreated());
//    }
}
