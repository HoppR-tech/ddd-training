create table basket_events (
  id bigint not null,
  basket_id bigint not null,
  sequence bigint not null,
  payload text not null,
  primary key (id)
)
