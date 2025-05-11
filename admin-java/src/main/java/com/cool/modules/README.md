# README

modules 下的模块推荐格式：

- modules/<模块名>

  - controller/admin

    - Admin<模块名><实体名>Controller

  - entity

    - <模块名><实体名>Entity

如：

- modules/accompany

    - controller/admin
    
        - AdminAccompany<u>Schedule</u>Controller
        - AdminAccompany<u>Staff</u>Controller
    
    - entity
    
        - Accompany<u>Schedule</u>Entity
        - Accompany<u>Staff</u>Entity

- modules/patient

    - controller/admin
    
        - AdminPatient<u>Info</u>Controller
        - AdminPatient<u>MedicalRecord</u>Controller
    
    - entity
    
        - Patient<u>Info</u>Entity
        - Patient<u>MedicalRecord</u>Entity
