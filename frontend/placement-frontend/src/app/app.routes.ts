import { Routes } from '@angular/router';
import { Home } from './Components/home/home';
import { Register } from './Components/register/register';
import { Login } from './Components/login/login';

export const routes: Routes = [

    {path:'',component:Home},
    {path:'register',component:Register},
    {path:'login',component:Login}
];
