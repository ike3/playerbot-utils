<%@include file="header.jsp" %>

<body ng-app="monitoring">
	<div class="container" role="main" ng-controller="MonitoringController">
		<div class="page-header">
			<h1>
            {{botName}}
            <div class="col-sm-2 pull-right">
                <input type="button" value="Pause" class="btn btn-warning" ng-click="stopLiveUpdate()" ng-disabled="!liveUpdate">
                <input type="button" value="Continue" class="btn btn-success" ng-click="startLiveUpdate()" ng-disabled="liveUpdate">
            </div>
            </h1>
		</div>

        <%@include file="search-results.jsp" %>

        <div ng-repeat="bot in bots" class="action-history">
            <div class="right-area">
                <table class="values">
                    <thead>
                        <th>Name</th>
                        <th>Value</th>
                    </thead>
                    <tbody>
                        <tr ng-repeat="v in bot.liveData.valueList">
                            <td>{{v.name}}</td>
                            <td title="{{v.value}}">{{v.value}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="right-area">
                <svg id="canvas" height="150" width="150">
                    <g transform="translate(75,75)scale(5)">
                        <g transform="translate({{bot.minimap.translate.x}},{{bot.minimap.translate.y}})">
                            <circle class="bot {{bot.liveData.state}}" cx="{{bot.minimap.bot.x}}" cy="{{bot.minimap.bot.y}}" r="1"/>
                            <circle class="target" cx="{{bot.minimap.target.x}}" cy="{{bot.minimap.target.y}}" r="1" />
                        </g>
                    </g>
                </svg>
            </div>
            <div ng-repeat="group in bot.actionHistory">
                <span ng-show="{{group.type == 'A'}}">
                    <span class="tab">&nbsp;</span><span class="tab">&nbsp;</span>
                    <span ng-repeat="action in group.actions" class="action {{action.result}}" title="{{action.result}}">
                        <img src="static/img/action.png" alt="action" title="action"/> {{action.text}}
                    </span>
                </span>

                <span ng-show="{{group.type == 'PUSH'}}">
                    <span class="tab">&nbsp;</span>
                    <span ng-repeat="action in group.actions" class="push">
                        <img src="static/img/push.png" alt="push" title="push" />{{action.text}}<sup>{{action.result}}</sup>
                    </span>
                </span>

                <span ng-show="{{group.type == 'info'}}">
                    <span ng-repeat="action in group.actions" class="info" alt="info" title="info" ><img src="static/img/info.png"/> {{action.text}}</sup></span>
                </span>

                <span ng-show="{{group.type == 'T'}}">

                    <span ng-repeat="action in group.actions" class="trigger">
                        <img src="static/img/trigger.png" alt="trigger" title="trigger" /> {{action.text}}
                    </span>
                </span>
            </div>
        </div>

	</div>
</body>

</html>