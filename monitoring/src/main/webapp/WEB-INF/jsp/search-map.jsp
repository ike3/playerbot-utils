<%@include file="header.jsp" %>

<div class="row" id="bot-search-results" >
	<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
    <svg ng-repeat="map in maps" id="canvas" height="{{map.size.y}}" width="{{map.size.x}}" class="bot-map bot-map-{{map.id}}" ng-hide="botsLoading">
        <g transform="scale({{map.scale}})">
            <g transform="translate({{map.translate.x}},{{map.translate.y}})">
                <g ng-repeat="bot in map.bots">
                    <circle class="bot {{bot.liveData.state}}" cx="{{bot.x}}" cy="{{bot.y}}" r="{{bot.r}}">
                        <title>{{bot.name}}</title>
                    </circle>
                </g>
                <g ng-repeat="bot in map.bots">
                    <rect class="hover" x="{{bot.x - bot.r}}" y="{{bot.y - bot.r}}" width="{{2*bot.r}}" height="{{2*bot.r}}"
                        bot-name="{{bot.name}}"
                        ng-click="openBot(bot)"/>
                </g>
            </g>
        </g>
    </svg>
</div>
