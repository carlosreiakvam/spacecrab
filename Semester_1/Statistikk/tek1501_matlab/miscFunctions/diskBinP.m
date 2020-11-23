function [pRet] = diskBinP(n,p,x)
pRet = nchoosek(n,x)*p^(x)*(1-p)^(n-x);
end

