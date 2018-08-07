<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Vending Machine</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">    
        <style type="text/css">
            .item {
                border: solid black 2px;
                margin: 1em 1.5em 1em 1.5em; 
            }

            .item:hover {
                background-color:lightgreen;
            }

            .w_nine {
                width: 9em;
            }

            .centered {
                text-align: center;
            }

            .top_3_black {
                border-top: solid black 3px;
            }

            .m_0_auto {
                margin: 0 auto;
            }
        </style>
    </head>
    <body>
        <div style="width: 92.5%; margin: .5em auto; border-bottom: solid black 4px;">
            <h1 style="text-align: center;">Vending Machine</h1>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-8" id="itemContainer">
                    <div class="row">
                        <c:set var="counter" value="1" scope="page" />
                        <c:forEach var="currentProduct" items="${productList}">
                            <form class="col-md-3 item" name="selectedIndex${counter}" action="selectProduct" method="POST" onclick="document.forms['selectedIndex${counter}'].submit();">
                                <input type="hidden" name="selectedIndex" value="${counter}"/>
                                <p style="margin-top: .75em;"><span style="background-color: black; padding: 0.25em; color: white;"><c:out value="${counter}"/></span></p>
                                <p style="text-align: center; font-size: 14px; font-weight: bold;"><c:out value="${currentProduct.productName}"/></p>
                                <p style="text-align: center;">$<c:out value="${currentProduct.productPrice}"/></p>
                                <p style="text-align: center; font-size: 14px;">Quantity Left: <c:out value="${currentProduct.productQuantity}"/></p>
                            </form>
                            <c:set var="counter" value="${counter + 1}" scope="page"/>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-lg-4">
                    <form class="centered" name="addMoney" action="addMoney" method="POST">
                        <div class="form-group">
                            <label for="totalMoney" class="col-form-label">Total $ In</label>
                            <input type="text" name="totalMoney" class="form-control form-control-sm centered m_0_auto" id="totalMoney" style="width: 7em;" value="${totalMoney}" readonly/>
                        </div>
                        <div class="form-group">
                            <button type="submit" name="moneyAdded" class="btn btn-outline-dark btn-sm w_nine" id="addDollar" value="DOLLAR">Add Dollar</button>
                            <button type="submit" name="moneyAdded" class="btn btn-outline-dark btn-sm w_nine" id="addQuarter" value="QUARTER">Add Quarter</button>
                        </div>
                        <div class="form-group">
                            <button type="submit" name="moneyAdded" class="btn btn-outline-dark btn-sm w_nine" id="addDime" value="DIME">Add Dime</button>
                            <button type="submit" name="moneyAdded" class="btn btn-outline-dark btn-sm w_nine" id="addNickel" value="NICKEL">Add Nickel</button>
                        </div>
                    </form>
                    <form class="top_3_black" name="makePurchase" action="makePurchase" method="POST">
                        <div class="form-group centered">
                            <label for="vendingMessage" class="col-form-label">Messages</label>
                            <input type="text" class="form-control form-control-sm m_0_auto" id="vendingMessage" value="${vendingMessages}" readonly/>
                        </div>
                        <div class="form-group form-row">
                            <label for="itemSelection" class="col-sm-2 col-form-label align-middle">Item:</label>
                            <div class="col-sm-3">
                                <input type="text" value="${productIndex}" class="form-control form-control-sm centered m_0_auto" id="itemSelection" style="width: 3em;" readonly/>
                            </div>
                        </div>
                        <div class="form-group centered">
                            <button type="submit" class="btn btn-outline-dark btn-sm w_nine" id="makePurchase">Make Purchase</button>
                        </div>
                    </form>
                    <form class="centered top_3_black" name="getChange" action="getChange" method="POST">
                        <div class="form-group">
                            <label for="returnedChange" class="col-form-label">Change</label>
                            <input type="text" name="returnedChange" id="returnedChange" class="form-control form-control-sm centered m_0_auto" value="${returnedChange}" readonly/>
                        </div>

                        <div class="form-group">
                            <button type="submit" name="returnChange" class="btn btn-outline-dark btn-sm w_nine">Change Return</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>

