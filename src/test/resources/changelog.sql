create table public.basket_items (
   basket_id text not null,
   product_ref text not null,
   quantity bigint not null,
   primary key (basket_id, product_ref)
);

create table public.basket_events (
  id text not null,
  basket_id text not null,
  sequence bigint not null,
  payload text not null,
  primary key (id)
);
