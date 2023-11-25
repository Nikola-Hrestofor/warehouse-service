create table if not exists warehouse
(
    id              serial  primary key,
    action          boolean, --true - add;  false - remove
    amount          numeric,
    cost            numeric,
    type            varchar,
    child_id         serial,
    order_number     varchar,
    in_date_time     date default CURRENT_DATE
);