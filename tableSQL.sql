USE [report_iam]
GO
/****** Object:  Table [dbo].[dtkbm_updates]    Script Date: 03/04/2020 16.30.56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dtkbm_updates](
	[full_name] [varchar](255) NOT NULL,
	[user_id] [varchar](255) NOT NULL,
	[login_id] [varchar](255) NOT NULL,
	[first_name] [varchar](100) NOT NULL,
	[middle_name] [varchar](100) NOT NULL,
	[last_name] [varchar](100) NOT NULL,
	[activation_date] [date] NULL,
	[STRING_05] [varchar](100) NULL,
	[expiration_date] [date] NOT NULL,
	[employee_status] [varchar](50) NULL,
	[manager] [varchar](50) NULL,
	[manager_employee_number] [varchar](50) NULL,
	[INTEGER_04] [varchar](50) NULL,
	[alternate_email] [varchar](100) NULL,
	[mobile_phone] [varchar](30) NULL,
	[STRING_00] [varchar](50) NULL,
	[STRING_01] [varchar](50) NULL,
	[department] [varchar](50) NULL,
	[STRING_02] [varchar](50) NULL,
	[CASE_EXACT_STRING04] [varchar](50) NULL,
	[cost_center] [varchar](100) NULL,
	[CASE_EXACT_STRING03] [varchar](50) NULL,
	[STRING_03] [varchar](50) NULL,
	[CASE_EXACT_STRING02] [varchar](50) NULL,
	[STRING_04] [varchar](50) NULL,
	[CASE_EXACT_STRING01] [varchar](50) NULL,
	[STRING_08] [varchar](50) NULL,
	[CASE_EXACT_STRING00] [varchar](50) NULL,
	[STRING_09] [varchar](50) NULL,
	[STRING_06] [varchar](50) NULL,
	[STRING_07] [varchar](50) NULL,
	[employee_type] [varchar](50) NULL,
	[enable] [tinyint] NULL,
	[last_update] [date] NOT NULL,
	[is_onboard] [int] NOT NULL,
	[is_update] [int] NOT NULL,
	[updated_attr] [varchar](30) NULL,
	[upd_success] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[dtobm_updates]    Script Date: 03/04/2020 16.30.56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[dtobm_updates](
	[full_name] [varchar](255) NOT NULL,
	[user_id] [varchar](255) NOT NULL,
	[login_id] [varchar](255) NOT NULL,
	[first_name] [varchar](100) NOT NULL,
	[middle_name] [varchar](100) NOT NULL,
	[last_name] [varchar](100) NOT NULL,
	[activation_date] [date] NULL,
	[STRING_05] [varchar](100) NULL,
	[expiration_date] [date] NULL,
	[employee_status] [varchar](50) NULL,
	[manager] [varchar](50) NULL,
	[manager_employee_number] [varchar](50) NULL,
	[INTEGER_04] [varchar](50) NULL,
	[alternate_email] [varchar](100) NULL,
	[mobile_phone] [varchar](30) NULL,
	[STRING_00] [varchar](50) NULL,
	[STRING_01] [varchar](50) NULL,
	[department] [varchar](50) NULL,
	[STRING_02] [varchar](50) NULL,
	[CASE_EXACT_STRING04] [varchar](50) NULL,
	[cost_center] [varchar](100) NULL,
	[CASE_EXACT_STRING03] [varchar](50) NULL,
	[STRING_03] [varchar](50) NULL,
	[CASE_EXACT_STRING02] [varchar](50) NULL,
	[STRING_04] [varchar](50) NULL,
	[CASE_EXACT_STRING01] [varchar](50) NULL,
	[STRING_08] [varchar](50) NULL,
	[CASE_EXACT_STRING00] [varchar](50) NULL,
	[STRING_09] [varchar](50) NULL,
	[STRING_06] [varchar](50) NULL,
	[STRING_07] [varchar](50) NULL,
	[employee_type] [varchar](50) NULL,
	[enable] [tinyint] NULL,
	[last_update] [date] NOT NULL,
	[is_onboard] [int] NOT NULL,
	[is_update] [int] NOT NULL,
	[updated_attr] [varchar](30) NULL,
	[upd_success] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_updates]    Script Date: 03/04/2020 16.30.56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_updates](
	[full_name] [varchar](255) NOT NULL,
	[user_id] [varchar](255) NOT NULL,
	[login_id] [varchar](255) NOT NULL,
	[first_name] [varchar](100) NOT NULL,
	[middle_name] [varchar](100) NOT NULL,
	[last_name] [varchar](100) NOT NULL,
	[activation_date] [date] NULL,
	[STRING_05] [varchar](100) NULL,
	[expiration_date] [date] NULL,
	[employee_status] [varchar](50) NULL,
	[manager] [varchar](50) NULL,
	[manager_employee_number] [varchar](50) NULL,
	[INTEGER_04] [varchar](50) NULL,
	[alternate_email] [varchar](100) NULL,
	[mobile_phone] [varchar](30) NULL,
	[STRING_00] [varchar](50) NULL,
	[STRING_01] [varchar](50) NULL,
	[department] [varchar](50) NULL,
	[STRING_02] [varchar](50) NULL,
	[CASE_EXACT_STRING04] [varchar](50) NULL,
	[cost_center] [varchar](100) NULL,
	[CASE_EXACT_STRING03] [varchar](50) NULL,
	[STRING_03] [varchar](50) NULL,
	[CASE_EXACT_STRING02] [varchar](50) NULL,
	[STRING_04] [varchar](50) NULL,
	[CASE_EXACT_STRING01] [varchar](50) NULL,
	[STRING_08] [varchar](50) NULL,
	[CASE_EXACT_STRING00] [varchar](50) NULL,
	[STRING_09] [varchar](50) NULL,
	[STRING_06] [varchar](50) NULL,
	[STRING_07] [varchar](50) NULL,
	[employee_type] [varchar](50) NULL,
	[enable] [tinyint] NULL,
	[last_update] [date] NOT NULL,
	[is_onboard] [int] NOT NULL,
	[is_update] [int] NOT NULL,
	[updated_attr] [varchar](30) NULL,
	[upd_success] [int] NULL
) ON [PRIMARY]
GO
