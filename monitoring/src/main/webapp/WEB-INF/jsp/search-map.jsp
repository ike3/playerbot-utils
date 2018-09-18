<%@include file="header.jsp" %>

<div class="row" id="bot-search-results" >
	<div ng-show="botsLoading" class="loading-spiner"><img src="static/img/ajax-loader.gif" /></div>
    <div id="destDiv"></div>
    <div style="display:none" id="sourceDiv">
    <svg1 id="source" ng-repeat="map in maps" id="canvas" height="450" width="300" class="bot-map bot-map-{{map.id}}" ng-hide="botsLoading">
        <g transform="scale({{map.scale.x}},{{map.scale.y}})">
            <g transform="translate({{map.translate.x}},{{map.translate.y}})rotate({{map.rotate.a}})">
                <g ng-repeat="bot in map.bots">
                    <circle class="bot {{bot.liveData.state}}" cx="{{bot.x}}" cy="{{bot.y}}" r="{{bot.r}}"/>
                </g>
                <g ng-repeat="bot in map.bots">
                    <rect class="hover" x="{{bot.x - bot.r}}" y="{{bot.y - bot.r}}" width="{{2*bot.r}}" height="{{2*bot.r}}"
                        bot-name="{{bot.name}}"
                        ng-click="openBot(bot)">
                            <title>{{bot.title}}</title>
                    </rect>
                </g>
            </g>
        </g>
    </svg1>
    </div>
</div>

<script type="text/javascript">
window.setInterval(function() {
    var sourceDiv = document.getElementById("sourceDiv");
    var destDiv = document.getElementById("destDiv");
    var svg = sourceDiv.innerHTML.replace(/svg1/ig, "svg");
    if (destDiv.innerHTML != svg) destDiv.innerHTML = svg;
}, 500);
</script>