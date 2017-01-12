# ACTIVITI BPMN2.0 示例说明

**简介：**

> A standard Business Process Model and Notation (BPMN) will provide businesses with the capability of understanding their internal business procedures in a graphical notation and will give organizations the ability to communicate these procedures in a standard manner.

> 标准业务流程模型标语语言（BPMN），为企业提供以图形符号的方式表示其内部业务流程，并且将其作为标准进行沟通。

## BPMN2.0 XML举例

假期申请流程规则（这里只是流程规则的定义）`ask-for-vacation.bpmn20.xml` (xml格式)

<img src="https://www.activiti.org/userguide/images/api.vacationRequest.png" />

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<definitions id="definitions"
             targetNamespace="http://activiti.org/bpmn20"
             xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:activiti="http://activiti.org/bpmn">

  <process id="vacationRequest" name="Vacation request">

    <startEvent id="request" activiti:initiator="employeeName">
      <extensionElements>
        <activiti:formProperty id="numberOfDays" name="Number of days" type="long" value="1" required="true"/>
        <activiti:formProperty id="startDate" name="First day of holiday (dd-MM-yyy)" datePattern="dd-MM-yyyy hh:mm" type="date" required="true" />
        <activiti:formProperty id="vacationMotivation" name="Motivation" type="string" />
      </extensionElements>
    </startEvent>
    <sequenceFlow id="flow1" sourceRef="request" targetRef="handleRequest" />

    <userTask id="handleRequest" name="Handle vacation request" >
      <documentation>
        ${employeeName} would like to take ${numberOfDays} day(s) of vacation (Motivation: ${vacationMotivation}).
      </documentation>
      <extensionElements>
         <activiti:formProperty id="vacationApproved" name="Do you approve this vacation" type="enum" required="true">
          <activiti:value id="true" name="Approve" />
          <activiti:value id="false" name="Reject" />
        </activiti:formProperty>
        <activiti:formProperty id="managerMotivation" name="Motivation" type="string" />
      </extensionElements>
      <potentialOwner>
        <resourceAssignmentExpression>
          <formalExpression>management</formalExpression>
        </resourceAssignmentExpression>
      </potentialOwner>
    </userTask>
    <sequenceFlow id="flow2" sourceRef="handleRequest" targetRef="requestApprovedDecision" />

    <exclusiveGateway id="requestApprovedDecision" name="Request approved?" />
    <sequenceFlow id="flow3" sourceRef="requestApprovedDecision" targetRef="sendApprovalMail">
      <conditionExpression xsi:type="tFormalExpression">${vacationApproved == 'true'}</conditionExpression>
    </sequenceFlow>

    <task id="sendApprovalMail" name="Send confirmation e-mail" />
    <sequenceFlow id="flow4" sourceRef="sendApprovalMail" targetRef="theEnd1" />
    <endEvent id="theEnd1" />

    <sequenceFlow id="flow5" sourceRef="requestApprovedDecision" targetRef="adjustVacationRequestTask">
      <conditionExpression xsi:type="tFormalExpression">${vacationApproved == 'false'}</conditionExpression>
    </sequenceFlow>

    <userTask id="adjustVacationRequestTask" name="Adjust vacation request">
      <documentation>
        Your manager has disapproved your vacation request for ${numberOfDays} days.
        Reason: ${managerMotivation}
      </documentation>
      <extensionElements>
        <activiti:formProperty id="numberOfDays" name="Number of days" value="${numberOfDays}" type="long" required="true"/>
        <activiti:formProperty id="startDate" name="First day of holiday (dd-MM-yyy)" value="${startDate}" datePattern="dd-MM-yyyy hh:mm" type="date" required="true" />
        <activiti:formProperty id="vacationMotivation" name="Motivation" value="${vacationMotivation}" type="string" />
        <activiti:formProperty id="resendRequest" name="Resend vacation request to manager?" type="enum" required="true">
          <activiti:value id="true" name="Yes" />
          <activiti:value id="false" name="No" />
        </activiti:formProperty>
      </extensionElements>
      <humanPerformer>
        <resourceAssignmentExpression>
          <formalExpression>${employeeName}</formalExpression>
        </resourceAssignmentExpression>
      </humanPerformer>
    </userTask>
    <sequenceFlow id="flow6" sourceRef="adjustVacationRequestTask" targetRef="resendRequestDecision" />

    <exclusiveGateway id="resendRequestDecision" name="Resend request?" />
    <sequenceFlow id="flow7" sourceRef="resendRequestDecision" targetRef="handleRequest">
      <conditionExpression xsi:type="tFormalExpression">${resendRequest == 'true'}</conditionExpression>
    </sequenceFlow>

     <sequenceFlow id="flow8" sourceRef="resendRequestDecision" targetRef="theEnd2">
      <conditionExpression xsi:type="tFormalExpression">${resendRequest == 'false'}</conditionExpression>
    </sequenceFlow>
    <endEvent id="theEnd2" />
  </process>
</definitions>

```
## BPMN2.0 XML说明

**定义规则属性**

```xml
<process id="vacationRequest" name="Vacation request">
```
- `id` 规则ID，以后通过此ID进行检索等操作
- `name`  规则名称

**注**：此处意思:

> 定义一个叫`Vacation request`的流程规则，id 是`vacationRequest`

**定义规则启动相关属性和变量**

```xml
<startEvent id="request" activiti:initiator="employeeName">
  <extensionElements>
    <activiti:formProperty id="numberOfDays" name="Number of days" type="long" value="1" required="true"/>
    <activiti:formProperty id="startDate" name="First day of holiday (dd-MM-yyy)" datePattern="dd-MM-yyyy hh:mm" type="date" required="true" />
    <activiti:formProperty id="vacationMotivation" name="Motivation" type="string" />
  </extensionElements>
</startEvent>
```
- `startEvent`
    @ `id` ID标识
    @ `activiti:initiator` 流程启动变量
    - `extensionElements` 变量列表
        - `activiti:formProperty ` 创建此规则实例时需要初始化的表单变量
        @ `id` 变量标识
        @ `name` 变量显示名
        @ `type` 变量类型
        @ `require` 是否为必须

**注**：此处意思:

> 申请假期需要员工名称(`employeeName`)，以及申请假期的天数(`numberOfDays`)，开始时间(`startDate`)，请假事由(`vacationMotivation`)

**定义规则流程衔接**

```xml
<sequenceFlow id="flow1" sourceRef="request" targetRef="handleRequest" />
```

- `sequenceFlow` 定义前后衔接的流程
    @ `id` 流程阶段ID
    @ `sourceRef` 流程规则中的上一步
    @ `targetRef` 流程规则中的下一步

**定义任务**

```xml
<userTask id="handleRequest" name="Handle vacation request" >
  <documentation>
    ${employeeName} would like to take ${numberOfDays} day(s) of vacation (Motivation: ${vacationMotivation}).
  </documentation>
  <extensionElements>
     <activiti:formProperty id="vacationApproved" name="Do you approve this vacation" type="enum" required="true">
      <activiti:value id="true" name="Approve" />
      <activiti:value id="false" name="Reject" />
    </activiti:formProperty>
    <activiti:formProperty id="managerMotivation" name="Motivation" type="string" />
  </extensionElements>
  <potentialOwner>
    <resourceAssignmentExpression>
      <formalExpression>management</formalExpression>
    </resourceAssignmentExpression>
  </potentialOwner>
</userTask>
```

- `userTask` 用户任务
    @ `id` 任务ID
    @ `name` 任务名称
    - `documentation` 任务描述，其中使用了`${}`表达式输出变量
    - `extensionElements` 任务中需要的变量
    - `potentialOwner` 潜在的所有者（白话：定义由谁来处理）

**注**：此处意思:

> `manangement` 处理申请假期的工单，需要决定(`vacationApproved`)对于申请是同意（`Approve`）还是否定（`Reject`）,以及对应的理由（`managerMotivation`）

**定义决策网关**

```xml
<sequenceFlow id="flow2" sourceRef="handleRequest" targetRef="requestApprovedDecision" />
<exclusiveGateway id="requestApprovedDecision" name="Request approved?" />
<sequenceFlow id="flow3" sourceRef="requestApprovedDecision" targetRef="sendApprovalMail">
  <conditionExpression xsi:type="tFormalExpression">${vacationApproved == 'true'}</conditionExpression>
</sequenceFlow>
```

- `flow2` 和 `flow3` 之间建立决策网关 `exclusiveGateway`
- `exclusiveGateway` 决策网关
    @ `id` 网关ID，后续需要决策后执行的步骤需要将此ID作为`sourceRef`
    - `conditionExpression` 条件语句，只有满足此条件才会执行其`targetRef`对应的流程步骤

**注**：此处意思:

> 如果`management`同意（`${vacationApproved == 'true'}`）假期申请，则进入`sendApprovalMail`发送邮件流程

**自动任务（不需要人干预的任务）**

```xml
<task id="sendApprovalMail" name="Send confirmation e-mail" />
```

**结束工作流任务**

```xml
<endEvent id="theEnd1" />
```

<b>XML中其余流程语义与以上内容相识，此处就不在獒述！！！</b>