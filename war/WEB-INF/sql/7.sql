ALTER TABLE `sshop`.`shop_country` add(freeDePrice float);
ALTER TABLE `sshop`.`shop_country` add(freeAdDePrice float);

update shop_country set freeDePrice = 80;
update shop_country set freeAdDePrice = 120;