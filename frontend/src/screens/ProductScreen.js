import { useParams } from "react-router-dom";

const ProductScreen=()=>{
   const params=useParams();
   const {id}=params;
   return<h1>{id}</h1>

}
export default ProductScreen;