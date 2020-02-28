#define EXPECT(case,arg0,arg1,pred,msg) \
{ bool res = pred(arg0,arg1); case.do_test(false,__FILE__,__LINE__,res,msg,arg0,arg1);}

#define EXPECT_EQ(case,arg0,arg1)  EXPECT(case,arg0,arg1,pred_eq,"Expect failed ==")

void test_is_subs(TestCase& tc)
{
    EXPECT_EQ(tc,true,is_ssubs("ab","ab"));
}