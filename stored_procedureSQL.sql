USE [report_iam]
GO
/****** Object:  StoredProcedure [dbo].[countDisable]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDisable] @begin_date date, @end_date date
AS
SELECT COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS count,
		last_update
		from dbo.user_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[countDisableDTKBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDisableDTKBM] @begin_date date, @end_date date
AS
SELECT COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS count,
		last_update
		from dbo.dtkbm_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[countDisableDTOBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countDisableDTOBM] @begin_date date, @end_date date
AS
SELECT COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS count,
		last_update
		from dbo.dtobm_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[countOnboard]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countOnboard] @begin_date date, @end_date date
AS
SELECT 
 COUNT(distinct CASE WHEN is_onboard = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS count,
last_update 
FROM dbo.user_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[countOnboardDTKBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countOnboardDTKBM] @begin_date date, @end_date date
AS
SELECT COUNT(DISTINCT user_id) AS count, last_update FROM dbo.dtkbm_updates where is_onboard=1 and last_update between @begin_date AND @end_date GROUP by last_update;
GO
/****** Object:  StoredProcedure [dbo].[countOnboardDTOBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countOnboardDTOBM] @begin_date date, @end_date date
AS
SELECT COUNT(DISTINCT user_id) AS count, last_update FROM dbo.dtobm_updates where is_onboard=1 and last_update between @begin_date AND @end_date GROUP by last_update;
GO
/****** Object:  StoredProcedure [dbo].[countUpdate]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countUpdate] @begin_date date, @end_date date
AS
SELECT COUNT(DISTINCT user_id) AS count, last_update FROM dbo.user_updates where is_update=1 and last_update between @begin_date AND @end_date GROUP by last_update;
GO
/****** Object:  StoredProcedure [dbo].[countUpdateDTKBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countUpdateDTKBM] @begin_date date, @end_date date
AS
SELECT COUNT(DISTINCT user_id) AS count, last_update FROM dbo.dtkbm_updates where is_update=1 and last_update between @begin_date AND @end_date GROUP by last_update;
GO
/****** Object:  StoredProcedure [dbo].[countUpdateDTOBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[countUpdateDTOBM] @begin_date date, @end_date date
AS
SELECT COUNT(DISTINCT user_id) AS count, last_update FROM dbo.dtobm_updates where is_update=1 and last_update between @begin_date AND @end_date GROUP by last_update;
GO
/****** Object:  StoredProcedure [dbo].[GetAllUser]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetAllUser]
AS
SELECT TOP 5 * from dbo.user_updates;
GO
/****** Object:  StoredProcedure [dbo].[getDtkbmDisabled]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtkbmDisabled] @begin_date date, @end_date date
AS
SELECT * FROM dbo.dtkbm_updates where is_update=1 and enable=0 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date AND @end_date ORDER by last_update;
GO
/****** Object:  StoredProcedure [dbo].[getDtkbmOnboard]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtkbmOnboard] @begin_date date, @end_date date
AS
SELECT distinct user_id, * FROM dbo.dtkbm_updates WHERE is_onboard=1  and last_update between @begin_date AND @end_date ;
GO
/****** Object:  StoredProcedure [dbo].[getDtkbmUpdates]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtkbmUpdates] @begin_date date, @end_date date
AS
SELECT distinct user_id, * FROM dbo.dtkbm_updates WHERE is_update=1  and last_update between @begin_date AND @end_date ;
GO
/****** Object:  StoredProcedure [dbo].[getDtobmDisabled]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtobmDisabled] @begin_date date, @end_date date
AS
SELECT * FROM dbo.dtobm_updates where is_update=1 and enable=0 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date AND @end_date ORDER by last_update;
GO
/****** Object:  StoredProcedure [dbo].[getDtobmOnboard]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtobmOnboard] @begin_date date, @end_date date
AS
SELECT distinct user_id, * FROM dbo.dtobm_updates WHERE is_onboard=1  and last_update between @begin_date AND @end_date ;
GO
/****** Object:  StoredProcedure [dbo].[getDtobmUpdates]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getDtobmUpdates] @begin_date date, @end_date date
AS
SELECT distinct user_id, * FROM dbo.dtobm_updates WHERE is_update=1  and last_update between @begin_date AND @end_date ;
GO
/****** Object:  StoredProcedure [dbo].[getSummaryReport]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getSummaryReport] @begin_date date, @end_date date
AS
SELECT
    COUNT(distinct CASE WHEN is_update = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countUpdate,
    COUNT(distinct CASE WHEN is_onboard = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countOnboard,
    COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countDisabled,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%mobile_phone%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS phone,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%alternate_email%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS email,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%full_name%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS name,
    last_update 
FROM dbo.user_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[getUserDisabled]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserDisabled] @begin_date date, @end_date date
AS
SELECT distinct(USER_ID), * FROM dbo.user_updates where is_update=1 and enable=0 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date AND @end_date ORDER by last_update;
GO
/****** Object:  StoredProcedure [dbo].[getUserOnboard]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserOnboard] @begin_date date, @end_date date
AS
SELECT distinct user_id, * FROM dbo.user_updates WHERE is_onboard=1  and last_update between @begin_date AND @end_date ;
GO
/****** Object:  StoredProcedure [dbo].[getUserUpdates]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[getUserUpdates] @begin_date date, @end_date date
AS
SELECT  * FROM dbo.user_updates WHERE is_update=1  and last_update between @begin_date AND @end_date and user_id IN (select distinct user_id from dbo.user_updates) ;
GO
/****** Object:  StoredProcedure [dbo].[summaryDTKBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[summaryDTKBM] @begin_date date, @end_date date
AS
SELECT
    COUNT(distinct CASE WHEN is_update = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countUpdate,
    COUNT(distinct CASE WHEN is_onboard = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countOnboard,
    COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countDisabled,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%mobile_phone%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS phone,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%alternate_email%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS email,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%full_name%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS name,
    last_update 
FROM dbo.dtkbm_updates GROUP BY last_update;
GO
/****** Object:  StoredProcedure [dbo].[summaryDTOBM]    Script Date: 03/04/2020 16.29.38 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[summaryDTOBM] @begin_date date, @end_date date
AS
SELECT
    COUNT(distinct CASE WHEN is_update = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countUpdate,
    COUNT(distinct CASE WHEN is_onboard = 1 and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countOnboard,
    COUNT(distinct CASE WHEN enable = 0 and is_update=1 and upd_success=1 and employee_status NOT LIKE '%Active%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS countDisabled,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%mobile_phone%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS phone,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%alternate_email%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS email,
    COUNT(distinct CASE WHEN is_update = 1 and upd_success=1 and updated_attr LIKE '%full_name%' and last_update between @begin_date and @end_date THEN user_id ELSE NULL END) AS name,
    last_update 
FROM dbo.dtobm_updates GROUP BY last_update;
GO

/****** Object:  StoredProcedure [dbo].[getUserLogin]    Script Date: 17/05/20 18:56:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
Create PROCEDURE [dbo].[getUserLogin]( 
	-- Add the parameters for the stored procedure here
	@user varchar(50),
	@pass varchar(50)
)
AS
	DECLARE
	@count int,
	@login int
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
    SELECT @count = count(id) FROM userlogin WHERE username = @user and password = @pass  and status = 1;
    
    if (@count = 1)
		SELECT @login = login FROM userlogin WHERE username = @user and password = @pass  and status = 1;	
	
	if (@login = 1)
		SET @count = 2;
	
	SELECT @count;
END
