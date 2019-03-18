import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import App from './App';
import "./css/styles.css";
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Game from './Game.js';
import Board from './Board.js';

import Player from './Player.js';

import * as serviceWorker from './serviceWorker';

ReactDOM.render(<BrowserRouter>
    <Switch>
        <Route exact path='/' component={App} />
        <Route exact path='/game/:id' component={Game} />
        <Route exact path='/game/:id/board' component={Board} />
        <Route exact path='/game/:id/:name' component={Player} />
    </Switch>
</BrowserRouter>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
