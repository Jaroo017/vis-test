package com.osekj.vis.dataloader

import javax.sql.DataSource

class TestDataLoader extends AbstractTestDataLoader {
    TestDataLoader(DataSource dataSource) {
        super(dataSource)
    }

    void createDefaultTestData() {
        sql.executeUpdate('INSERT INTO PERMISSION (PERMISSION_ID, PERMISSION_NAME) VALUES (?, ?)', [1, 'LIST_USERS'])
        sql.executeUpdate('INSERT INTO PERMISSION (PERMISSION_ID, PERMISSION_NAME) VALUES (?, ?)', [2, 'CREATE_USERS'])
        sql.executeUpdate('INSERT INTO PERMISSION (PERMISSION_ID, PERMISSION_NAME) VALUES (?, ?)', [3, 'EDIT_USERS'])
        sql.executeUpdate('INSERT INTO PERMISSION (PERMISSION_ID, PERMISSION_NAME) VALUES (?, ?)', [4, 'DELETE_USERS'])

        createRole(-1, 'ADMIN_ROLE')
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-1, 1])
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-1, 2])
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-1, 3])
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-1, 4])
        sql.executeUpdate('INSERT INTO USER (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [-1, '$2a$10$2y1b/MSGK9Zvpdrb4hzpwO5EseOb8db.GGQkLZd5pjvzMq/YeeI9.', 'admin', -1])

        createRole(-2, 'LIST_USER_ROLE')
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-2, 1])
        sql.executeUpdate('INSERT INTO USER (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [-2, '$2a$10$2y1b/MSGK9Zvpdrb4hzpwO5EseOb8db.GGQkLZd5pjvzMq/YeeI9.', 'list', -2])

        createRole(-3, 'CREATE_USER_ROLE')
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-3, 2])
        sql.executeUpdate('INSERT INTO USER (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [-3, '$2a$10$2y1b/MSGK9Zvpdrb4hzpwO5EseOb8db.GGQkLZd5pjvzMq/YeeI9.', 'create', -3])

        createRole(-4, 'EDIT_USER_ROLE')
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-4, 3])
        sql.executeUpdate('INSERT INTO USER (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [-4, '$2a$10$2y1b/MSGK9Zvpdrb4hzpwO5EseOb8db.GGQkLZd5pjvzMq/YeeI9.', 'edit', -4])

        createRole(-5, 'DELETE_USER_ROLE')
        sql.executeUpdate('INSERT INTO ROLE_PERMISSIONS (ROLE_ID, PERMISSION_ID) VALUES (?, ?)', [-5, 4])
        sql.executeUpdate('INSERT INTO USER (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [-5, '$2a$10$2y1b/MSGK9Zvpdrb4hzpwO5EseOb8db.GGQkLZd5pjvzMq/YeeI9.', 'delete', -5])
    }

    void createRole(Long roleId, String roleName) {
        sql.executeUpdate('insert into role (role_id, role_name) VALUES (?, ?)', [roleId, roleName])
    }

    void createUser(long userId, String username, String password, long roleId) {
        sql.executeUpdate('insert into user (user_id, password, username, role_id) VALUES (?, ?, ?, ?)',
                [userId, username, password, roleId])
    }

    void deleteAll() {
        sql.executeUpdate('delete from user')
        sql.executeUpdate('delete from role_permissions')
        sql.executeUpdate('delete from Role')
        sql.executeUpdate('delete from permission')
    }
}
