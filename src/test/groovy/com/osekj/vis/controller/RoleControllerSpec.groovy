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
class RoleControllerSpec extends Specification {

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

    def 'should get all roles'() {
        expect:
        mockMvc.perform(get('/v1/vis-test/roles')
                .header('Authorization', buildJWT(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath('\$', hasSize(5)))
    }

    def 'should save role'() {
        expect:
        mockMvc.perform(post('/v1/vis-test/roles')
                .header('Authorization', buildJWT(ADMIN))
                .content(getRoleJson())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('\$.id').exists())
                .andExpect(jsonPath('\$.name').value('test'))
                .andExpect(jsonPath('\$.permissions', hasSize(1)))
    }

    def 'should edit role'() {
        expect:
        mockMvc.perform(put('/v1/vis-test/roles/{id}', -5)
                .header('Authorization', buildJWT(ADMIN))
                .content(getRoleJson())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath('\$.id').value(-5))
                .andExpect(jsonPath('\$.name').value('test'))
                .andExpect(jsonPath('\$.permissions', hasSize(1)))
    }

    def 'should delete role'() {
        given:
        testDataLoader.createRole(99, 'ROLE')

        expect:
        mockMvc.perform(delete('/v1/vis-test/roles/{id}', 99)
                .header('Authorization', buildJWT(ADMIN)))
                .andExpect(status().isOk())
    }

    def 'should return 404 when role does not exists'() {
        expect:
        mockMvc.perform(put('/v1/vis-test/roles/{id}', 99)
                .header('Authorization', buildJWT(ADMIN))
                .content(getRoleJson())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
        mockMvc.perform(delete('/v1/vis-test/roles/{id}', 99)
                .header('Authorization', buildJWT(ADMIN)))
                .andExpect(status().isNotFound())
    }

    @Unroll
    def 'should get error when user is not admin'() {
        expect:
        mockMvc.perform(get('/v1/vis-test/roles')
                .header('Authorization', buildJWT(user)))
                .andExpect(status().isForbidden())
        mockMvc.perform(post('/v1/vis-test/roles')
                .header('Authorization', buildJWT(user))
                .content(getRoleJson())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden())
        mockMvc.perform(put('/v1/vis-test/roles/{id}', 99)
                .header('Authorization', buildJWT(user))
                .content(getRoleJson())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden())
        mockMvc.perform(delete('/v1/vis-test/roles/{id}', 99)
                .header('Authorization', buildJWT(user)))
                .andExpect(status().isForbidden())

        where:
        user << [CREATE_USER, DELETE_USER, EDIT_USER, LIST_USER]
    }

    private static String getRoleJson() {
        '{\n' +
                '    "name": "test",\n' +
                '    "permissions": [\n' +
                '        "DELETE_USERS"\n' +
                '    ]\n' +
                '}'
    }
}