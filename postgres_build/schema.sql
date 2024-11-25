grant all privileges on database spring_db to spring_app;

create table employee_tb (
    employee_id bigint not null, 
    employee_cpf varchar(255) not null, 
    employee_name varchar(255) not null, 
    primary key (employee_id)
);

alter table if exists employee_tb add constraint employee_cpf_uk unique (employee_cpf);
create sequence employee_seq start with 1 increment by 1;

create table package_tb (
    package_id bigint not null, 
    package_content varchar(255) not null, 
    package_height float(53) not null, 
    package_length float(53) not null, 
    package_weigth float(53) not null, 
    package_width float(53) not null, 
    shipment_id bigint, 
    primary key (package_id)
);

create sequence package_seq start with 1 increment by 1;

create table shipment_tb (
    shipment_id bigint not null, 
    shipment_additional_info varchar(255), 
    shipment_cep varchar(255), 
    shipment_number varchar(255), 
    shipment_state varchar(255), 
    shipment_street varchar(255), 
    shipment_arrival_dt date not null, 
    shipment_arrived boolean not null, 
    shipment_receiver varchar(255) not null, 
    employee_id bigint not null, 
    primary key (shipment_id)
);

create sequence shipment_seq start with 1 increment by 1;

alter table if exists package_tb add constraint package_shipment_fk foreign key (shipment_id) references shipment_tb;
alter table if exists shipment_tb add constraint employee_fk foreign key (employee_id) references employee_tb;

create table if not exists activity_log_tb (
    id serial primary key,
    table_name text not null,
    operation_type text not null,
    created_at timestamp default now()
);

create or replace function activity_log_trigger() returns trigger as $$
begin
    insert into activity_log_tb (table_name, operation_type)
    values (TG_TABLE_NAME, TG_OP);

    return new;
end;
$$ language plpgsql;

create or replace trigger activity_log_tb_trigger
after insert or update or delete on shipment_tb
for each row
execute function activity_log_trigger();

create or replace trigger activity_log_tb_trigger
after insert or update or delete on employee_tb
for each row
execute function activity_log_trigger();

create or replace trigger activity_log_tb_trigger
after insert or update or delete on package_tb
for each row
execute function activity_log_trigger();

grant all privileges 
on table activity_log_tb to spring_app;

grant all privileges 
on table package_tb to spring_app;

grant all privileges 
on table shipment_tb to spring_app;

grant all privileges 
on table employee_tb to spring_app;


grant all privileges 
on sequence activity_log_tb_id_seq to spring_app;

grant all privileges 
on table package_seq to spring_app;

grant all privileges 
on table shipment_seq to spring_app;

grant all privileges 
on table employee_seq to spring_app;
