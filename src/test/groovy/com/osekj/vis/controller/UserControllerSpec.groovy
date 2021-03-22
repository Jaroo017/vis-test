package com.osekj.vis.controller

import com.osekj.vis.dataloader.TestDataLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static com.osekj.vis.config.TestUser.*
import static com.osekj.vis.config.util.SecurityUtil.buildJWT
import static org.hamcrest.Matchers.hasSize
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    TestDataLoader testDataLoader

    def setup() {
        testDataLoader.createDefaultTestData()
    }

    def cleanup() {
        testDataLoader.deleteAll()
    }

    @Unroll
    def 'should get all users'() {
        expect:
        mockMvc.perform(get('/v1/vis-test/users')
                .header('Authorization', buildJWT(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('\$', hasSize(5)))

        where:
        user << [ADMIN, LIST_USER]
    }

    @Unroll
    def 'should save user'() {
        expect:
        mockMvc.perform(post('/v1/vis-test/users')
                .header('Authorization', buildJWT(user))
                .content('{\n' +
                        '    "username": "test",\n' +
                        '    "password": "test",\n' +
                        '    "roleId": -5\n' +
                        '}')
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('\$.id').exists())
                .andExpect(jsonPath('\$.username').value('test'))
                .andExpect(jsonPath('\$.role').value('DELETE_USER_ROLE'))

        where:
        user << [ADMIN, CREATE_USER]
    }

    @Unroll
    def 'should edit user'() {
        expect:
        mockMvc.perform(put('/v1/vis-test/users/{id}', -5)
                .header('Authorization', buildJWT(user))
                .content('{\n' +
                        '    "username": "test",\n' +
                        '    "roleId": -4\n' +
                        '}')
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath('\$.id').exists())
                .andExpect(jsonPath('\$.username').value('test'))
                .andExpect(jsonPath('\$.role').value('EDIT_USER_ROLE'))

        where:
        user << [ADMIN, EDIT_USER]
    }

    @Unroll
    def 'should delete user'() {
        given:
        testDataLoader.createUser(99L, 'test', 'test', -4L)

        expect:
        mockMvc.perform(delete('/v1/vis-test/users/{id}', 99)
                .header('Authorization', buildJWT(user)))
                .andExpect(status().isOk())

        where:
        user << [ADMIN, DELETE_USER]
    }

    def 'should return 404 when user does not exists'() {
        expect:
        mockMvc.perform(put('/v1/vis-test/users/{id}', 99)
                .header('Authorization', buildJWT(ADMIN))
                .content('{\n' +
                        '    "username": "test",\n' +
                        '    "roleId": 4\n' +
                        '}')
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
        mockMvc.perform(delete('/v1/vis-test/users/{id}', 99)
                .header('Authorization', buildJWT(ADMIN)))
                .andExpect(status().isNotFound())
    }
}
