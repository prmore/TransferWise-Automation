##########TransferWise Converter Automation##########

Steps to run tests:
	1)	Import solution in Eclipse
	2)	If you want to change input data please find input data excel file at path "\TransferWiseAutomation\src\test\resources\DataInput.xlsx"
	3)	Then Right click on solution and select 'Run As --> Maven clean' 
		and then again right click on solution and select 'Run As --> Maven install'
			OR
		Right click on solution and select 'Run As --> Maven build...' and in Goals type 'clean install' and hit 'Run'
	4)	Maven build will start and after the completion of execution, we can find Extent report under following path “\TransferWiseAutomation\test-output\ExecutionReport.html”
	5)	Execution logs will be generated under folder: "\TransferWiseAutomation\logs\app.log"