
alter table shop_user ADD(billingSameAsPrimary boolean);
alter table shop_user ADD(cityP varchar(200));
alter table shop_user ADD(cityB varchar(200));

update shop_user set  billingSameAsPrimary = 1 ;