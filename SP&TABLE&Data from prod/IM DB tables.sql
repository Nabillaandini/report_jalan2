USE [IM_DB]
GO
/****** Object:  Table [dbo].[event12_5]    Script Date: 20/05/2020 15.48.01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event12_5](
	[eventid] [nvarchar](50) NOT NULL,
	[state] [int] NOT NULL,
	[tasksessionid] [nvarchar](50) NOT NULL,
	[created_on] [datetime] NOT NULL,
	[last_access_time] [datetime] NOT NULL,
	[name] [nvarchar](512) NOT NULL,
	[description] [nvarchar](2000) NOT NULL,
	[type] [int] NOT NULL,
	[next_state] [int] NULL,
	[taskeventwfapproval] [nvarchar](512) NULL,
	[approvalstatus] [nvarchar](50) NULL,
	[decisionmaker] [nvarchar](512) NULL,
	[approvaltime] [datetime] NULL,
	[finalstatus] [nvarchar](50) NULL,
	[attributes] [nvarchar](2000) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tasksession12_5]    Script Date: 20/05/2020 15.48.01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tasksession12_5](
	[tasksessionid] [nvarchar](50) NOT NULL,
	[state] [int] NOT NULL,
	[created_time] [datetime] NOT NULL,
	[last_access_time] [datetime] NOT NULL,
	[user_dn] [nvarchar](512) NOT NULL,
	[org_dn] [nvarchar](512) NOT NULL,
	[environmentid] [nvarchar](50) NOT NULL,
	[name] [nvarchar](512) NOT NULL,
	[description] [nvarchar](2000) NOT NULL,
	[acknowledged] [char](1) NOT NULL,
	[parent_of_wf_id] [nvarchar](50) NULL,
	[action_type] [int] NOT NULL,
	[priority] [int] NULL,
	[initiatorid] [nvarchar](50) NULL,
	[nesting_type] [int] NOT NULL,
	[next_state] [int] NULL,
	[taskeventwfapproval] [nvarchar](512) NULL,
	[approvalstatus] [nvarchar](50) NULL,
	[decisionmaker] [nvarchar](512) NULL,
	[approvaltime] [datetime] NULL,
	[finalstatus] [nvarchar](50) NULL,
	[attributes] [nvarchar](2000) NULL,
	[numericSequence] [bigint] NULL
) ON [PRIMARY]
GO
