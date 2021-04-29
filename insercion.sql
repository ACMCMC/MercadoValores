Insert into usuario_regulador (id,clave,saldo,comision_actual)
Values ('Reg',crypt('1234', gen_salt('bf')),0.0,0.1);

Insert into usuario_mercado (id,clave,saldo,direccion,telefono,estado)
Values ('Tim',crypt('1234', gen_salt('bf')),1000.73,'Gotham','+1578543221','DADO_DE_ALTA'),
('Clark',crypt('1234', gen_salt('bf')),300.12,'Metropolis','+1578123456','SOLICITANDO_ALTA'),
('Bruce',crypt('1234', gen_salt('bf')),1500.23,'Brasilia','+55675432876','SOLICITANDO_BAJA'),
('Opti',crypt('1234', gen_salt('bf')),190000.12,'Gotham','+1578537145','DADO_DE_ALTA'),
('SI',crypt('1234', gen_salt('bf')),110000000.23,'Barcelona','+34951123456','SOLICITANDO_ALTA'),
('WW',crypt('1234', gen_salt('bf')),150000000.23,'Madrid','+34902432876','SOLICITANDO_ALTA');

Insert into usuario_empresa (id,cif,nombre_comercial)
VALUES ('Opti','B22222222','Optitrón'),
('WW','W27272727','Wayne Enterprise'),
('SI','S11111111','Stark Industries');

Insert into usuario_inversor (id,dni,nombre_completo)
Values ('Tim','39759783E','Timothy Drake Wayne'),
('Clark','12774663S','Clark Kent El'),
('Bruce','24327634B','Bruce Wayne');

Insert into beneficios (id,fecha_pago,importe_por_participacion,num_participaciones)
Values ('Opti','2021/12/31 17:00:00.00', 4.00, 0),
('WW','2022/04/26 14:00:00.00',100, 0),
('WW','2022/04/26 15:00:00.00',0, 5),
('SI','2021/5/9 00:00:00.00',50, 3);

Insert into tener_participaciones (id1,id2,num_participaciones)
Values ('Bruce','Opti', 6),
('Tim','WW',10),
('WW','SI',5);

Insert into anuncio_venta (id1,id2,num_participaciones,fecha,precio,comision_en_fecha)
Values ('Bruce','Opti', 3,'2021/4/12 13:00:00.59',14.0,0.05),
('Tim','WW',2,'2021/4/2 14:20:50.29',25.0,0.07),
('WW','SI',1,'2021/3/2 10:24:52.29',23.24,0.1);
            
            
Insert into compra(empresa,comprador,fecha)
Values ('Opti','Bruce','2020/4/12 13:00:00.59'),
('Opti','Tim','2020/5/12 13:00:00.59'),
('Opti','Bruce','2021/4/12 13:00:00.59');

INSERT INTO parte_compra(id_compra,vendedor,precio,cantidad)
Values(1,'Opti',10.0,6),
(2,'Bruce',11.0,4),
(3,'Tim',15.0,4);
