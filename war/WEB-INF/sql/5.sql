alter table shop_user add(height float);
alter table shop_user add(weight float);
alter table shop_user add(age float);
alter table shop_user add(shoulder varchar(50));
alter table shop_user add(chest varchar(50));
alter table shop_user add(stomch varchar(50));
alter table shop_user add(posture varchar(50));
alter table shop_user add(shirtNeck float);
alter table shop_user add(jacketShirtLenght float);
alter table shop_user add(chestSize float);
alter table shop_user add(stomachSize float);
alter table shop_user add(jacketHips float);
alter table shop_user add(shoulderSize float);
alter table shop_user add(sleeveLength float);
alter table shop_user add(bicepSize float);
alter table shop_user add(wristSize float);
alter table shop_user add(pantsLength float);
alter table shop_user add(waist float);
alter table shop_user add(crotch float);
alter table shop_user add(thighSize float);
alter table shop_user add(kneeSize float);
alter table shop_user add(suitMeasurement boolean);

alter table shop_order add(height float);
alter table shop_order add(weight float);
alter table shop_order add(age float);
alter table shop_order add(shoulder varchar(50));
alter table shop_order add(chest varchar(50));
alter table shop_order add(stomch varchar(50));
alter table shop_order add(posture varchar(50));
alter table shop_order add(shirtNeck float);
alter table shop_order add(jacketShirtLenght float);
alter table shop_order add(chestSize float);
alter table shop_order add(stomachSize float);
alter table shop_order add(jacketHips float);
alter table shop_order add(shoulderSize float);
alter table shop_order add(sleeveLength float);
alter table shop_order add(bicepSize float);
alter table shop_order add(wristSize float);
alter table shop_order add(pantsLength float);
alter table shop_order add(waist float);
alter table shop_order add(crotch float);
alter table shop_order add(thighSize float);
alter table shop_order add(kneeSize float);
alter table shop_order add(suitMeasurement boolean);


update shop_order set suitMeasurement=0, kneeSize= 0,  thighSize = 0, crotch = 0, waist = 0, pantsLength = 0, wristSize = 0, bicepSize = 0, 

sleeveLength= 0, shoulderSize = 0, jacketHips = 0, stomachSize = 0, chestSize = 0, jacketShirtLenght=0, shirtNeck=0, age=0, weight =0, height=0;

update shop_user set suitMeasurement=0, kneeSize= 0,  thighSize = 0, crotch = 0, waist = 0, pantsLength = 0, wristSize = 0, bicepSize = 0, 

sleeveLength= 0, shoulderSize = 0, jacketHips = 0, stomachSize = 0, chestSize = 0, jacketShirtLenght=0, shirtNeck=0, age=0, weight =0, height=0;






