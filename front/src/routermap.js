import React ,{Component}from 'react'
import {BrowserRouter,Route,Link} from 'react-router-dom'
import Home from './home'
import Admin from "./admin";
import Login from "./login"

class RouterMap extends Component {
    render() {
        return (
            <BrowserRouter>
            <div>
                <Link to={"/home"}>home</Link>
                <Route path={"/home"} component={Home} />
                <Route path={"/admin"} component={Admin}/>
                <Route path={"/login"} component={Login}/>
            </div>
            </BrowserRouter>
        )

    }
}

export default RouterMap