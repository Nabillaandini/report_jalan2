USE [IM_DB]
GO
/****** Object:  StoredProcedure [dbo].[countDeprovision]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDeprovision]  @begin_date datetime, @end_date datetime
AS
SELECT COUNT(*), CAST(approvaltime AS DATE) FROM dbo.tasksession12_5 where name LIKE '%Offboarding Tasks%'  and description not like '%failed%'and approvaltime between @begin_date and @end_date+1 group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countDeprovMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDeprovMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(*) FROM dbo.tasksession12_5 where description like '%Offboarding Tasks task%' and description not like '%User K%' and description not like '%User O%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countDeprovNonSAP]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDeprovNonSAP]  @begin_date datetime, @end_date datetime,@apps nvarchar(30)
AS
SELECT COUNT(*), CAST(approvaltime AS DATE) FROM dbo.tasksession12_5 where name LIKE '%Offboarding Tasks%'   and description like @apps and description not like '%failed%'and approvaltime between @begin_date and @end_date+1 group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[countDeprovSummary]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDeprovSummary] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT
YEAR(approvaltime) yearlist,MONTH(approvaltime) as monthlist,
count (CASE when description like '%Offboarding Tasks task%' and description not like '%User K%' and description not like '%User O%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countSAP,
count (CASE when description like '%Offboarding Tasks task%' and description like'%User K%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDKTBM,
count (CASE when description like '%Offboarding Tasks task%' and description like '%User O%' and description not like '%User K%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDOTBM
from dbo.tasksession12_5 where MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY MONTH(approvaltime),YEAR(approvaltime);
GO
/****** Object:  StoredProcedure [dbo].[countDeprovWeek]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[countDeprovWeek]( @begin datetime, @end datetime) AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6), 
count (case when description like '%Offboarding Tasks task%' and description not like '%User O%' and description not like '%User K%' and description not like '%failed%' then description else null end)as deprovSAP,
count (case when description like '%Offboarding Tasks task%' and description not like '%User O%' and description like '%User K%' and description not like '%failed%' then description else null end)as deprovDTOBM,
count (case when description like '%Offboarding Tasks task%' and description not like '%User K%' and description like '%User O%' and description not like '%failed%' then description else null end)as deprovDTKBM
FROM IM_DB.dbo.tasksession12_5
WHERE name LIKE '%Offboarding Tasks%'  
	AND description not like '%failed%'
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
end
GO
/****** Object:  StoredProcedure [dbo].[countOnboardDaily]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardDaily] @begin_date datetime, @end_date datetime
AS
SELECT CAST(approvaltime AS DATE),COUNT(distinct description) FROM dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description not like '%User K%' and description not like '%User O%' and description not like '%failed%' and 
approvaltime between @begin_date and @end_date+1 GROUP BY CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC

GO
/****** Object:  StoredProcedure [dbo].[countOnboardDailyNonSAP]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardDailyNonSAP] @begin_date datetime, @end_date datetime, @apps nvarchar(30)
AS
SELECT CAST(approvaltime AS DATE),COUNT(distinct description) FROM dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description like @apps and description not like '%failed%' and 
approvaltime between @begin_date and @end_date+1 GROUP BY CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC

GO
/****** Object:  StoredProcedure [dbo].[countOnboardMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(distinct description) FROM dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description not like '%User K%' and description not like '%User O%'  and description not like '%failed%'
 and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;

GO
/****** Object:  StoredProcedure [dbo].[countOnboardMonthlyNonSAP]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardMonthlyNonSAP] @month1 int, @year1 int, @month2 int, @year2 int,@apps nvarchar(30)
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(distinct description) FROM dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description like @apps and description not like '%failed%'
 and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;

GO
/****** Object:  StoredProcedure [dbo].[countOnboardWeekly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardWeekly] @begin datetime, @end datetime
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6), count(distinct description)
from dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description not like '%User K%' and description not like '%User O%'  and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
	ORDER BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
end
GO
/****** Object:  StoredProcedure [dbo].[countOnboardWeeklyNonSAP]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countOnboardWeeklyNonSAP] @begin datetime, @end datetime,@apps nvarchar(30)
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6), count(distinct description)
from dbo.tasksession12_5 where 
 name like 'Onboarding User Task' and description like @apps and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
	ORDER BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
end
GO
/****** Object:  StoredProcedure [dbo].[countProvDTKBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvDTKBM] @begin_date datetime, @end_date datetime
AS
SELECT  CAST(approvaltime AS DATE),COUNT(*) FROM dbo.tasksession12_5 where name like '%DTKBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1 group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[countProvDTOBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvDTOBM] @begin_date datetime, @end_date datetime
AS
SELECT  CAST(approvaltime AS DATE),COUNT(*) FROM dbo.tasksession12_5 where name like '%DTOBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1 group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[countProvision]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvision] @begin_date datetime, @end_date datetime, @apps nvarchar(30)
AS
SELECT  CAST(approvaltime AS DATE),COUNT(distinct description) FROM dbo.tasksession12_5 where name like @apps and description like '%Approval task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1  group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[countProvisionApps]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
Create PROCEDURE  [dbo].[countProvisionApps] @begin_date datetime, @end_date datetime, @apps nvarchar(30)
AS
SELECT  CAST(approvaltime AS DATE),COUNT(distinct description) FROM dbo.tasksession12_5 where name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1  group by CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[countProvMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvMonthly] @month1 int, @year1 int, @month2 int, @year2 int,@apps nvarchar(30)
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(distinct description) FROM dbo.tasksession12_5 where  name like @apps and description like '%Approval task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countProvMonthlyApps]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvMonthlyApps] @month1 int, @year1 int, @month2 int, @year2 int,@apps nvarchar(30)
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(distinct description) FROM dbo.tasksession12_5 where  name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countProvMonthlyDTKBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvMonthlyDTKBM] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(distinct description) FROM dbo.tasksession12_5 where name like '%DTKBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countProvMonthlyDTOBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvMonthlyDTOBM] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(*) FROM dbo.tasksession12_5 where name like '%DTOBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countProvWeekly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvWeekly] @begin datetime, @end datetime,@apps nvarchar(50)
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),count(distinct description)
from dbo.tasksession12_5 where 
 name like @apps and description like '%Approval task%' and name not like '%dummy%' and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
end
GO
/****** Object:  StoredProcedure [dbo].[countProvWeeklyApps]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countProvWeeklyApps] @begin datetime, @end datetime,@apps nvarchar(50)
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),count(distinct description)
from dbo.tasksession12_5 where 
 name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6)
end
GO
/****** Object:  StoredProcedure [dbo].[countResetPassAD]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countResetPassAD] @begin_date datetime, @end_date datetime
AS
SELECT 
 CAST(approvaltime AS DATE),description,
COUNT(*)
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1   group by CAST(approvaltime AS DATE),description ORDER BY CAST(approvaltime AS DATE) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countResetPassADMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countResetPassADMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
select a.year, a.month,count(a.counter) as counter from
(SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,COUNT(*) as counter,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)>1 ) a group by a.year,a.month ORDER BY a.year,a.month ASC;
GO
/****** Object:  StoredProcedure [dbo].[countSumPassApps]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countSumPassApps] @month1 int, @year1 int, @month2 int, @year2 int
AS
select YEAR(approvaltime),MONTH(approvaltime),
count(case when description like 'Assign user %RAOS - Reset Password%'  then description else null end )  raospw,
count(case when description like 'Assign user %MMS - RESET PASSWORD%'  then description else null end )  mmspw,
count(case when description like 'Assign user %DTOBM - Reset Password%'  then description else null end )  dtobmpw,
count(case when description like 'Assign user %DTKBM - Reset Password%'  then description else null end )  dtkbmpw,
count(case when description like 'Assign user %SAP - Reset Password%'  then description else null end )  sappw,
count(case when description like 'Assign user %Web Bill Gateway - Reset Password%'  then description else null end )  wbgpw,
count(case when description like 'Assign user %SKD KPEI - Reset Password%'  then description else null end )  skdpw,
count(case when description like 'Assign user %AAP - Reset Password%'  then description else null end )  aappw,
count(case when description like 'Assign user %SAP eBudgeting - Reset Password%'  then description else null end )  ebudpw,
count(case when description like 'Assign user %SAP eHCMS - Reset Password%'  then description else null end )  ehcmsdpw,
count(case when description like 'Assign user %SAP FICO - Reset Password%'  then description else null end )  ficopw,
count(case when description like 'Assign user %SAP SRM - Reset Password%'  then description else null end )  srmpw,
count(case when description like 'Assign user %SKD MM - Reset Password%'  then description else null end )  mmpw,
count(case when description like 'Assign user %Web Corporate Payable - Reset Password%'  then description else null end )  wcppw
from dbo.event12_5 where description like '% - Reset Password%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[countUserRequest]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[countUserRequest] @begin_date datetime, @end_date datetime,@apps nvarchar(30)
AS
select CAST(approvaltime AS DATE),count(*) from dbo.tasksession12_5 where name like '%Approval Target Apps%' and name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between  @begin_date and @end_date+1 group by CAST(approvaltime AS DATE),description order by CAST(approvaltime AS DATE) asc;
GO
/****** Object:  StoredProcedure [dbo].[deprovMonthDTKBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deprovMonthDTKBM]  @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(*) FROM dbo.tasksession12_5 where description like '%Offboarding Tasks task%' and description not like '%User O%' and description like '%User K%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[deprovMonthDTOBM]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deprovMonthDTOBM]  @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),COUNT(*) FROM dbo.tasksession12_5 where description like '%Offboarding Tasks task%' and description not like '%User K%' and description like '%User O%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[deprovMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[deprovMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime), description FROM dbo.tasksession12_5 where description like '%Offboarding Tasks task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getDeprovData]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDeprovData]  @begin_date datetime, @end_date datetime
AS
SELECT CAST(approvaltime AS DATE), description FROM dbo.tasksession12_5 where name LIKE '%Offboarding Tasks%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1 ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[getDeprovMonthly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDeprovMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),description FROM dbo.tasksession12_5 where description like '%Offboarding Tasks task%'  and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime),description ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getDeprovWeekly]    Script Date: 20/05/2020 15.48.35 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDeprovWeekly] @begin datetime, @end datetime
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
FROM dbo.tasksession12_5 where  name LIKE '%Offboarding Tasks%' and description not like '%failed%' and
 approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
end
GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassADDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassADDaily] @begin_date datetime, @end_date datetime
AS
 SELECT 
  CAST(approvaltime AS DATE) as tgl ,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)>1 order by tgl asc;
GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassADMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassADMonthly]@month1 int, @year1 int, @month2 int, @year2 int 
AS
SELECT 
 YEAR(approvaltime),MONTH(approvaltime),description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)>1  order by YEAR(approvaltime),MONTH(approvaltime) asc;
GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassADWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassADWeekly] @begin datetime, @end datetime
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,description
FROM dbo.tasksession12_5
WHERE description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%'
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)>1 
ORDER BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassAPPMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassAPPMonthly] @month1 int, @year1 int, @month2 int, @year2 int, @apps nvarchar(30),@query nvarchar(50)
AS
SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)>1 ORDER BY YEAR(approvaltime),MONTH(approvaltime)
GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassAppsDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassAppsDaily] @begin_date datetime, @end_date datetime, @apps nvarchar(30), @query nvarchar(50)
AS
SELECT 
  CAST(approvaltime AS DATE) as tgl ,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)>1 ORDER BY tgl ASC;


GO
/****** Object:  StoredProcedure [dbo].[getmultiResetPassAPPWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getmultiResetPassAPPWeekly] @begin datetime, @end datetime,@apps nvarchar(30),@query nvarchar(50)
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,description
FROM dbo.event12_5
WHERE name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' 
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)>1 
GO
/****** Object:  StoredProcedure [dbo].[getProvisionData]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getProvisionData]  @begin_date datetime, @end_date datetime, @apps nvarchar(30)
AS
SELECT CAST(approvaltime AS DATE), description FROM dbo.tasksession12_5 where  name like @apps and description like '%Approval task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1 ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[getProvisionDataApps]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getProvisionDataApps]  @begin_date datetime, @end_date datetime, @apps nvarchar(30)
AS
SELECT CAST(approvaltime AS DATE), description FROM dbo.tasksession12_5 where   name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between @begin_date and @end_date+1 ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[getProvMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[getProvMonthly] @month1 int, @year1 int, @month2 int, @year2 int,@apps nvarchar(30)
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),description FROM dbo.tasksession12_5 where  name like @apps and description like '%Approval task%' and name not like '%dummy%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime),description ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getProvMonthlyApps]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[getProvMonthlyApps] @month1 int, @year1 int, @month2 int, @year2 int,@apps nvarchar(30)
AS
SELECT YEAR(approvaltime),MONTH(approvaltime),description FROM dbo.tasksession12_5 where  name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime),description ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getProvWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[getProvWeekly] @begin datetime, @end datetime,@apps nvarchar(50)
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
from dbo.tasksession12_5 where 
 name like @apps and description like '%Approval task%' and name not like '%dummy%' and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
end
GO
/****** Object:  StoredProcedure [dbo].[getProvWeeklyApps]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[getProvWeeklyApps] @begin datetime, @end datetime,@apps nvarchar(50)
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
from dbo.tasksession12_5 where 
 name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%'
AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
end
GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassADDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassADDaily] @begin_date datetime, @end_date datetime
AS
 SELECT 
  CAST(approvaltime AS DATE) as tgl ,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)=1 order by tgl asc;
GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassADMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassADMonthly]@month1 int, @year1 int, @month2 int, @year2 int 
AS
SELECT 
 YEAR(approvaltime),MONTH(approvaltime),description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)=1  order by YEAR(approvaltime),MONTH(approvaltime) asc;
GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassADWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassADWeekly] @begin datetime, @end datetime
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,description
FROM dbo.tasksession12_5
WHERE description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%'
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)=1 
ORDER BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassAPPMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassAPPMonthly] @month1 int, @year1 int, @month2 int, @year2 int, @apps nvarchar(30),@query nvarchar(50)
AS
SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)=1 ORDER BY YEAR(approvaltime),MONTH(approvaltime)
GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassAppsDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassAppsDaily] @begin_date datetime, @end_date datetime, @apps nvarchar(30), @query nvarchar(50)
AS
SELECT 
  CAST(approvaltime AS DATE) as tgl ,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)=1 ORDER BY tgl ASC;


GO
/****** Object:  StoredProcedure [dbo].[getsingleResetPassAPPWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getsingleResetPassAPPWeekly] @begin datetime, @end datetime,@apps nvarchar(30),@query nvarchar(50)
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,description
FROM dbo.event12_5
WHERE name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' 
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)=1 
GO
/****** Object:  StoredProcedure [dbo].[getSummary]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSummary] @begin_date Date, @end_date Date
AS
SELECT
    COUNT(CASE WHEN  name LIKE '%Provisioning Create User%' and approvaltime between @begin_date and @end_date THEN 1 ELSE NULL END) AS countProvision,
	COUNT(CASE WHEN name LIKE '%Offboarding Tasks%' and approvaltime between @begin_date and @end_date THEN 1 ELSE NULL END) AS countDeprovision,
	 CAST(approvaltime AS DATE ) AS approvalDate
from dbo.tasksession12_5 where approvaltime between @begin_date and @end_date GROUP BY CAST(approvaltime AS DATE) ORDER BY CAST(approvaltime AS DATE) ASC
GO
/****** Object:  StoredProcedure [dbo].[getSummaryReport]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSummaryReport] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT 
YEAR(approvaltime),MONTH(approvaltime),
count(distinct case when name like 'Onboarding User Task'and description not like '%User K%' and description not like '%User O%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countonboard,
count(distinct case when description like '%Modify User task%' and name like 'Modify User' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countupdate,
count(distinct case when name like '%SAP eHCMS Approval Target Apps%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as sapehcms,
count(distinct case when name like '%DTOBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as dtobm,
count(distinct case when  name like '%DTKBM Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as dtkbm,
count(distinct case when  name like '%MMS Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as mms,
count(distinct case when  name like '%RAOS Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as raos,
count(distinct case when  name like '%Web Bill Gateway Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as wbg,
count(distinct case when description like '%Offboarding Task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDeprov,
count(distinct case when description like '%PortalPortal Change My Password task%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as resetDomain,
count(distinct case when description like '%Reset Password Target Apps No Approval%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as resetApps,
count(distinct case when name like 'SAP eBudgeting Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as ebudgeting,
count(distinct case when name like 'SAP FICO Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as fico,
count(distinct case when name like 'SAP MM Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as mm,
count(distinct case when name like 'SAP SRM Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as srm,
count(distinct case when name like 'SKD KPEI Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as skd,
count (distinct CASE when description like '%Offboarding Tasks task%' and description not like '%User K%' and description not like '%User O%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDeprovSAP,
count (distinct CASE when description like '%Offboarding Tasks task%' and description like'%User K%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDeprovDKTBM,
count (distinct CASE when description like '%Offboarding Tasks task%' and description like '%User O%' and description not like '%User K%' and description not like 'failed' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countDeprovDOTBM,
count(distinct case when  name like '%Web Corporate Payable Approval Target Apps%' and description like '%Approval Target Apps task%' and description not like '%failed%'  and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as wcp,
count(distinct case when name like 'AAP Approval' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as aap,
count(distinct case when name like 'Onboarding User Task'and description  like '%User O%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countonboardDTOBM,
count(distinct case when name like 'Onboarding User Task'and description like '%User K%' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 THEN description else null end) as countonboardDTKBM
from dbo.tasksession12_5  where MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;

select YEAR(approvaltime),MONTH(approvaltime),
count(distinct case when description like 'Assign user %RAOS - Reset Password%'  then description else null end )  raospw,
count(distinct case when description like 'Assign user %MMS - RESET PASSWORD%'  then description else null end )  mmspw,
count(distinct case when description like 'Assign user %DTOBM - Reset Password%'  then description else null end )  dtobmpw,
count(distinct case when description like 'Assign user %DTKBM - Reset Password%'  then description else null end )  dtkbmpw,
count(distinct case when description like 'Assign user %SAP - Reset Password%'  then description else null end )  sappw,
count(distinct case when description like 'Assign user %Web Bill Gateway - Reset Password%'  then description else null end )  wbgpw,
count(distinct case when description like 'Assign user %SKD KPEI - Reset Password%'  then description else null end )  skdpw,
count(distinct case when description like 'Assign user %AAP - Reset Password%'  then description else null end )  aappw,
count(distinct case when description like 'Assign user %SAP eBudgeting - Reset Password%'  then description else null end )  ebudpw,
count(distinct case when description like 'Assign user %SAP eHCMS - Reset Password%'  then description else null end )  ehcmsdpw,
count(distinct case when description like 'Assign user %SAP FICO - Reset Password%'  then description else null end )  ficopw,
count(distinct case when description like 'Assign user %SAP SRM - Reset Password%'  then description else null end )  srmpw,
count(distinct case when description like 'Assign user %SKD MM - Reset Password%'  then description else null end )  mmpw,
count(distinct case when description like 'Assign user %Web Corporate Payable - Reset Password%'  then description else null end )  wcppw
from dbo.event12_5 where description like '% - Reset Password%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 GROUP BY YEAR(approvaltime),MONTH(approvaltime) ORDER BY YEAR(approvaltime),MONTH(approvaltime) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getUserOnboard]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserOnboard] @begin_date datetime, @end_date datetime
AS
SELECT CAST(approvaltime AS DATE), description FROM dbo.tasksession12_5 where  name LIKE 'Onboarding User Task'  and description not like '%failed%' and approvaltime>= @begin_date and approvaltime<=@end_date+1 ORDER BY CAST(approvaltime AS DATE) ASC;
GO
/****** Object:  StoredProcedure [dbo].[getUserOnboardMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserOnboardMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
SELECT YEAR(approvaltime),MONTH(approvaltime), description FROM dbo.tasksession12_5 where  name LIKE 'Onboarding User Task'  and description not like '%failed%' and 
 MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2 group by YEAR(approvaltime),MONTH(approvaltime), description ORDER BY YEAR(approvaltime),MONTH(approvaltime) asc
GO
/****** Object:  StoredProcedure [dbo].[getUserOnboardWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserOnboardWeekly] @begin datetime, @end datetime
AS
begin
DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

SELECT dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
FROM dbo.tasksession12_5 where  name LIKE 'Onboarding User Task'  and description not like '%failed%' and
 approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
	GROUP BY dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description
end
GO
/****** Object:  StoredProcedure [dbo].[getUserRequest]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  [dbo].[getUserRequest] @begin_date datetime, @end_date datetime,@apps nvarchar(30)
AS
select CAST(approvaltime AS DATE),description from dbo.tasksession12_5 where name like '%Approval Target Apps%' and name like @apps and name not like '%dummy%' and description like '%Approval Target Apps task%' and description not like '%failed%' and approvaltime between  @begin_date and @end_date+1 group by CAST(approvaltime AS DATE),description order by CAST(approvaltime AS DATE) asc;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassADDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassADDaily] @begin_date datetime, @end_date datetime
AS
select a.tgl ,count(a.counter) as counter from
(SELECT 
  CAST(approvaltime AS DATE) as tgl ,COUNT(*) as counter,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)>1 ) a group by a.tgl ORDER BY a.tgl ASC;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassADMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassADMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
select a.year, a.month,count(a.counter) as counter from
(SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,COUNT(*) as counter,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)>1 ) a group by a.year,a.month ORDER BY a.year,a.month ASC;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassADWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassADWeekly] @begin datetime, @end datetime
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

select a.week_start, a.week_end ,count(a.counter) as counter from
(SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,COUNT(*) as counter
FROM dbo.tasksession12_5
WHERE description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%'
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)>1 ) a
group by a.week_start, a.week_end
ORDER BY  a.week_start, a.week_end ASC;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassAPPMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassAPPMonthly] @month1 int, @year1 int, @month2 int, @year2 int, @apps nvarchar(30),@query nvarchar(50)
AS
select a.year, a.month,count(a.counter) as counter from
(SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,COUNT(*) as counter,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)>1 ) a group by a.year,a.month ORDER BY a.year,a.month ASC;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassAppsDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassAppsDaily] @begin_date datetime, @end_date datetime, @apps nvarchar(30), @query nvarchar(50)
AS
select a.tgl ,count(a.counter) as counter from
(SELECT 
  CAST(approvaltime AS DATE) as tgl ,COUNT(*) as counter
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)>1 ) a group by a.tgl ORDER BY a.tgl ASC;
GO
/****** Object:  StoredProcedure [dbo].[multiResetPassAPPWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[multiResetPassAPPWeekly] @begin datetime, @end datetime,@apps nvarchar(30),@query nvarchar(50)
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

select a.week_start, a.week_end ,count(a.counter) as counter from
(SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,COUNT(*) as counter
FROM dbo.event12_5
WHERE name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' 
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)>1 ) a
group by a.week_start, a.week_end
ORDER BY  a.week_start, a.week_end ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassADDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassADDaily] @begin_date datetime, @end_date datetime
AS
select a.tgl ,count(a.counter) as counter from
(SELECT 
  CAST(approvaltime AS DATE) as tgl ,COUNT(*) as counter,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)=1 ) a group by a.tgl ORDER BY a.tgl ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassADMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassADMonthly] @month1 int, @year1 int, @month2 int, @year2 int
AS
select a.year, a.month,count(a.counter) as counter from
(SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,COUNT(*) as counter,description
FROM dbo.tasksession12_5 where description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)=1 ) a group by a.year,a.month ORDER BY a.year,a.month ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassADWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassADWeekly] @begin datetime, @end datetime
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

select a.week_start, a.week_end ,count(a.counter) as counter from
(SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,COUNT(*) as counter
FROM dbo.tasksession12_5
WHERE description like 'PortalPortal Change My Password task%' and name='PortalPortal Change My Password' and description not like '%failed%'
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)=1 ) a
group by a.week_start, a.week_end
ORDER BY  a.week_start, a.week_end ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassAPPMonthly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassAPPMonthly] @month1 int, @year1 int, @month2 int, @year2 int, @apps nvarchar(30),@query nvarchar(50)
AS
select a.year, a.month,count(a.counter) as counter from
(SELECT 
 YEAR(approvaltime) as year,MONTH(approvaltime) as month,COUNT(*) as counter,description
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and MONTH(approvaltime)>=@month1 and MONTH(approvaltime)<=@month2 and YEAR(approvaltime)>=@year1 and YEAR(approvaltime)<=@year2
GROUP BY YEAR(approvaltime),MONTH(approvaltime),description having count(*)=1 ) a group by a.year,a.month ORDER BY a.year,a.month ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassAppsDaily]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassAppsDaily] @begin_date datetime, @end_date datetime, @apps nvarchar(30), @query nvarchar(50)
AS
select a.tgl ,count(a.counter) as counter from
(SELECT 
  CAST(approvaltime AS DATE) as tgl ,COUNT(*) as counter
FROM dbo.event12_5 where name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' and  approvaltime between @begin_date and @end_date+1 
GROUP BY  CAST(approvaltime AS DATE),description having count(*)=1 ) a group by a.tgl ORDER BY a.tgl ASC;
GO
/****** Object:  StoredProcedure [dbo].[singleResetPassAPPWeekly]    Script Date: 20/05/2020 15.48.36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[singleResetPassAPPWeekly] @begin datetime, @end datetime,@apps nvarchar(30),@query nvarchar(50)
AS

DECLARE  @start_of_week DATE
DECLARE @end_of_week DATE

SET @start_of_week = dateadd(week, datediff(week, 0, @begin), 0);
SET @end_of_week = dateadd(week, datediff(week, 0, @end), 6);

select a.week_start, a.week_end ,count(a.counter) as counter from
(SELECT dateadd(week, datediff(week, 0, approvaltime), 0) as week_start, dateadd(week, datediff(week, 0, approvaltime), 6) as week_end ,COUNT(*) as counter
FROM dbo.event12_5
WHERE name like 'AssignProvisioningRoleEvent' and  description like @apps  and description like @query and  description not like '%failed%' 
	AND approvaltime>=@start_of_week 
	AND approvaltime<=@end_of_week
GROUP BY  dateadd(week, datediff(week, 0, approvaltime), 0), dateadd(week, datediff(week, 0, approvaltime), 6),description having count(*)=1 ) a
group by a.week_start, a.week_end
ORDER BY  a.week_start, a.week_end ASC;
GO
