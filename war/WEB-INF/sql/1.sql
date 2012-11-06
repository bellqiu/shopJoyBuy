
alter table shop_user ADD(address1P varchar(200));
alter table shop_user ADD(address1B varchar(200));

alter table shop_user ADD(fullNameP varchar(200));
alter table shop_user ADD(fullNameB varchar(200));

alter table shop_user ADD(address2P  varchar(200));
alter table shop_user ADD(address2B varchar(200));

alter table shop_user ADD(stateProvinceP varchar(200));
alter table shop_user ADD(stateProvinceB varchar(200));

alter table shop_user ADD(countryP int);
alter table shop_user ADD(countryB int);

alter table shop_user ADD(postalCodeP varchar(200));
alter table shop_user ADD(postalCodeB varchar(200));

alter table shop_user ADD(phoneP varchar(200));
alter table shop_user ADD(phoneB varchar(200));

alter table shop_user ADD(cityP varchar(200));
alter table shop_user ADD(cityB varchar(200));

update shop_user set  countryP = 0 , countryB = 0 ;