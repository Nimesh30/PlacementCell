// import { CanActivateFn, Router } from "@angular/router";
// import { inject } from "@angular/core";

// export const roleGuard: CanActivateFn = (route, state) => {

//   const router = inject(Router);

//   const token = localStorage.getItem('token');
//   const userRole = localStorage.getItem('role')?.toUpperCase();

//   if (!token || !userRole) {
//     router.navigate(['/login']);
//     return false;
//   }

//   const allowedRoles = route.data?.['roles'] as string[];

//   console.log("User Role:", userRole);
//   console.log("Allowed Roles:", allowedRoles);

//   if (allowedRoles && allowedRoles.includes(userRole)) {
//     return true;
//   }

//   console.log("Access denied");

//   // Smart redirect
//   if (userRole === 'ADMIN') {
//     router.navigate(['/adminlayout/admindashboard']);
//   } else {
//     router.navigate(['/layout/userdashboard']);
//   }

//   return false;
// };



import { CanActivateFn, Router } from "@angular/router";
import { inject } from "@angular/core";

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);

  const userRole = localStorage.getItem('role');
  const token = localStorage.getItem('token');

  const allowedRoles = route.data?.['roles'] as string[];

  // No token → not logged in → redirect to Home Page
  if (!token || token === 'null' || token === 'undefined') {
    router.navigate(['/']);
    return false;
  }

  if (allowedRoles && allowedRoles.length > 0) {
    if (!userRole || !allowedRoles.includes(userRole)) {
      // If ADMIN trying student route or vice versa, redirect appropriately
      if (userRole === 'ADMIN') {
        router.navigate(['/adminlayout/admindashboard']);
      } else {
        router.navigate(['/layout/userdashboard']);
      }
      return false;
    }
  }

  return true;
};