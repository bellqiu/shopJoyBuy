
alter table shop_order ADD(billingSameAsPrimary boolean);
alter table shop_order ADD(country int);
alter table shop_order ADD(bcountry int);
alter table shop_order ADD(customerBCountry varchar(200));

update shop_order set  billingSameAsPrimary = 1 ;
update shop_order set  country = 1 ;
update shop_order set  bcountry = 1 ;