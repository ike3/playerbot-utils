<%@include file="header.jsp" %>

<div class="row">
    <form id="bot-search-form" class="form-horizontal" role="form" ng-submit="search()">
        <div class="form-group">
            <label for="botName" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" name="botName" ng-model="botName" placeholder="Enter first symbols of a character name"/>
            </div>
        </div>
        <div class="form-group">
            <label for="botName" class="col-sm-2 control-label">Faction</label>
            <div class="col-sm-10">
                <div class="radio">
                  <label>
                    <input type="radio" name="faction" id="factionAlliance" value="Alliance" ng-model="faction">
                    Alliance
                  </label>
                </div>
                <div class="radio">
                  <label>
                    <input type="radio" name="faction" id="factionHorde" value="Horde" ng-model="faction" >
                    Horde
                  </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-2">
                <input type="submit" value="Find" class="btn btn-primary">
            </div>
        </div>
    </form>
</div>
